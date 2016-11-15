package edu.cmu.cs.cs214.hw4.core;

import javafx.util.Pair;

import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.Collection;
import java.util.ListIterator;
import java.util.Iterator;
import java.util.stream.Collectors;

/**
 * Represent the Scrabble game
 */
public class Game implements ScrabbleGame {
    //Max number of players
    private final int maxPlayers = 4;
    //Rack size for each player
    private final int rackSize = 7;
    //Scrabble board
    private Board board = new Board();
    //List of players playing the game (2-4)
    private List<Player> players = null;
    //Bag containing the letter tiles
    private Bag bag;
    //Set of words
    private Set<String> dictionary;
    //Instance of random
    private Random random;
    //Players who has the turn
    private Player whoseTurn;
    //Index of the current player
    private int currentPlayerIndex = 0;
    //Phase of the turn forward for backward
    private boolean isTurnPhaseForward;
    //Flag for end game
    private boolean isGameEnd;
    //Stores last move
    private Board lastBoard = new Board();
    //Store last tiles players
    private Map<Location, LetterTile> lastMoveTiles = new HashMap<>();
    //Stores the words scored in last move
    private List<String> lastMoveWords = new ArrayList();
    //Stores the score of last move (points gained)
    private int lastMoveScore;
    //Index of the player who played the last turn
    private int lastMovePlayerIndex;
    //Save the instance of Player's rack
    private List<LetterTile> lastMovePlayerRack;
    //To keep track of the loss turn feature
    private Map<Player, Integer> turnLossMap;
    //Add of subtract flag
    private final boolean add = true;
    //Counts the number of scoreless turns
    private int scorelessTurnCount = 0;
    //Instance of the special tile shop
    private SpecialTileShop specialTileShop = new SpecialTileShop();
    //Keeps special tile played in this turn
    private List<SpecialTile> specialTilesInTurn = new ArrayList<>();
    // The listeners who will be notified of changes in the game state
    private final List<GameChangeListener> gameChangeListeners  = new ArrayList<>();

    /**
     * Constructor
     *
     * @param playerNames List of names of all the players(2-4)
     * @param dictionary Set of words for dictionary
     * @param random instance of the Random
     */
    public Game(List<String> playerNames, Set<String> dictionary, Random random) {
        Objects.requireNonNull(playerNames, "Players list can't be null");
        assert(playerNames.size() <= maxPlayers && playerNames.size() > 1);
        addPlayers(playerNames);
        this.dictionary = Objects.requireNonNull(dictionary, "Dictionary can't be null");
        this.random = Objects.requireNonNull(random, "Object of random can't be null");
        bag = new Bag(this.random);

        assignRackToPlayers();
        clearTurnLossMap();
    }

    @Override
    public void addGameChangeListener(GameChangeListener listener) {
        gameChangeListeners.add(listener);
    }

    /**
     * Resets the turn loss map. 0 turns looses for all players
     */
    private void clearTurnLossMap() {
        turnLossMap = new HashMap<>();
        for (Player player: players)
            turnLossMap.put(player, 0);
    }

    /**
     * Helper method to add players to the game
     * @param playerNames list of names of players
     */
    private void addPlayers(List<String> playerNames) {
        players = new ArrayList<>();
        ListIterator iterator = playerNames.listIterator();
        while(iterator.hasNext()) {
            int playerId = iterator.nextIndex();
            players.add(new Player((String)iterator.next(), playerId));
        }
    }

    /**
     * Assigns a rack to each player
     */
    private void assignRackToPlayers() {
        for(Player player: players) {
            List<LetterTile> letterTiles = new ArrayList<>(rackSize);
            bag.replenishTiles(letterTiles);
            player.setRack(letterTiles);
        }
    }

    /**
     * Start the game
     */
    public void startGame() {
        isTurnPhaseForward = true;
        whoseTurn = currentPlayer();
        notifyPlayerChanged();
    }

    /**
     * Reverses the phase of the turns
     */
    void reverseTurnPhase() {
        isTurnPhaseForward = !isTurnPhaseForward;
    }

    /**
     * Get the current player (who has the turn)
     * @return Player
     */
    public Player currentPlayer() {
        return players.get(currentPlayerIndex);
    }

    /**
     * Get the instance of the board used for the game
     * @return Board
     */
    public Board getBoard() {
        return board;
    }

    /**
     * Progress the turn to next player
     */
    private void progressTurn() {
        if(isTurnPhaseForward) {
            if (currentPlayerIndex == players.size() - 1)
                currentPlayerIndex = 0;
            else
                currentPlayerIndex++;
        }
        else {
            if (currentPlayerIndex == 0)
                currentPlayerIndex = players.size() - 1;
            else
                currentPlayerIndex--;
        }
        whoseTurn = currentPlayer();
        int turnsToLoose = turnLossMap.get(whoseTurn);
        if(turnsToLoose > 0) {
            turnLossMap.remove(whoseTurn);
            turnLossMap.put(whoseTurn, turnsToLoose - 1);
            progressTurn();
        }
        else{
            List<LetterTile> rack = whoseTurn.getRack();
            bag.replenishTiles(rack);
            whoseTurn.setRack(rack);
        }
        specialTilesInTurn.clear();
        notifyPlayerChanged();
    }

    /**
     * Purchase a special tile for the player who as the turn
     * @param specialTileType specialTileType
     * @return SpecialTile
     */
    public SpecialTile purchaseSpecialTile(SpecialTileType specialTileType) {
        SpecialTile specialTile = specialTileShop.purchase(specialTileType, whoseTurn);
        specialTilesInTurn.add(specialTile);
        notifyMessage("Special Tile: " + specialTile.toString() + "purchased");
        notifyPlayerChanged();
        return specialTile;
    }

    /**
     * Place the special tile on the board
     * @param specialTile specialTile
     * @param location Location
     */
    public void placeSpecialTile(SpecialTile specialTile, Location location) {
        if(!specialTilesInTurn.contains(specialTile)) {
            if(whoseTurn.getSpecialRack().contains(specialTile)) {
                board.placeSpecialTile(specialTile, location, whoseTurn);
                whoseTurn.removeSpecialTile(specialTile);
                notifySpecialTilesPlaced(location, specialTile);
            }
            else
                throw new IllegalArgumentException("Special tile has not been purchased");
        }
        else{
            throw new IllegalArgumentException("Tiles purchased in this turn can't be placed");
        }
    }

    /**
     * Helper function to save the last move
     * @param letterTilesMap map of letter tile with location (for this turn)
     */
    private void saveLastMove(Map<Location, LetterTile> letterTilesMap) {
        lastMoveWords = new ArrayList<>();
        lastBoard = new Board(board);
        lastMoveTiles = new HashMap<>(letterTilesMap);
        lastMovePlayerIndex = currentPlayerIndex;
        lastMovePlayerRack = new ArrayList<>(whoseTurn.getRack());
    }

    /**
     * Place all letter tiles on the board
     * @param letterTilesMap Map of locations and letter tiles
     */
    public void placeLetterTiles(Map<Location, LetterTile> letterTilesMap) {
        Map<Location, LetterTile> tilesMap = new HashMap<>(letterTilesMap);
        if(tilesMap.size() == 0)
            throw new IllegalArgumentException("Move not valid");
        boolean success = isMoveValid(tilesMap);
        if(success) {
            //Save the last move (useful to restore the game)
            saveLastMove(letterTilesMap);
            board.placeAllLetterTiles(tilesMap);
            notifyLetterTilesPlaced(letterTilesMap);

            //Pre-activate special tiles
            for(Location location: tilesMap.keySet()) {
                preActivateSpecialTiles(board.squareAt(location).getSpecialTiles(), location);
            }

            lastMoveScore = calculateTotalScore(tilesMap);
            whoseTurn.addOrSubtractScore(lastMoveScore, add);

            //Post activate special tiles
            for(Location location: tilesMap.keySet()) {
                postActivateSpecialTiles(board.squareAt(location).getSpecialTiles(), location);
            }

            removePlayedTilesFromRack(letterTilesMap);
            notifyMessage("Words formed :" + lastMoveWords.toString() + " . Score: " + lastMoveScore);
            scorelessTurnCount = 0;
            checkGameEnd();
            progressTurn();
        }
        else {
            throw new IllegalArgumentException("Move not valid");
        }
    }

    /**
     * Remove the letter played in this turn from the players rack
     * @param letterTilesMap map of letter tile with location (for this turn)
     */
    private void removePlayedTilesFromRack(Map<Location, LetterTile> letterTilesMap) {
        List<LetterTile> rackCopy = whoseTurn.getRack();
        Map<Location, LetterTile> letterTileMapCopy = new HashMap<>(letterTilesMap);
        Iterator<Map.Entry<Location,LetterTile>> iter = letterTileMapCopy.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry<Location,LetterTile> entry = iter.next();
            for(int i = 0; i < rackCopy.size(); i++) {
                if (rackCopy.get(i).equals(entry.getValue())) {
                    iter.remove();
                    rackCopy.remove(i);
                    break;
                }
            }
        }
        whoseTurn.setRack(rackCopy);
    }

    /**
     * Helper method to activate the tiles before score is calculated
     * @param specialTileMap
     * @param location
     */
    private void preActivateSpecialTiles(Map<Player, SpecialTile>  specialTileMap, Location location) {
        for(SpecialTile specialTile: specialTileMap.values()) {
            specialTile.preScoringActivate(this, location);
        }
    }

    /**
     * Helper mehtod to activate the tiles after the score is calculated
     * @param specialTileMap
     * @param location
     */
    private void postActivateSpecialTiles(Map<Player, SpecialTile>  specialTileMap, Location location) {
        boolean isBoomTile = false;
        SpecialTile boomTile = null;
        Location boomTileLocation = null;
        for(SpecialTile specialTile: specialTileMap.values()) {
            if(specialTile.specialTileType().equals(SpecialTileType.Boom)) {
                isBoomTile = true;
                boomTileLocation = location;
                boomTile = specialTile;
            }
            else {
                specialTile.postScoringActivate(this, location);
                boomTile = specialTile;
            }
        }
        if(isBoomTile)
            boomTile.postScoringActivate(this, boomTileLocation);
    }

    /**
     * get the score of last move
     * @return score of the last move
     */
    public int getLastMoveScore() {
        return lastMoveScore;
    }

    /**
     * sets the score of the last move
     * @param score
     */
    void setLastMoveScore(int score) {
        lastMoveScore = lastMoveScore;
    }

    /**
     * reset last moves
     */
    private void resetLastMoves() {
        lastBoard = new Board();
        lastMoveTiles = new HashMap<>();
        lastMoveWords = new ArrayList();
        lastMovePlayerRack = new ArrayList<>();
        lastMoveScore = Integer.MIN_VALUE;
        lastMovePlayerIndex = Integer.MIN_VALUE;
    }

    Map<Location, LetterTile> getLastMoveTiles() {
        return new HashMap<>(lastMoveTiles);
    }

    /**
     * Calculate total score of one move
     * @param letterTilesMap map of locations as key and letter tile as values
     * @return score
     */
    int calculateTotalScore(Map<Location, LetterTile> letterTilesMap) {
        int result = 0;
        List<Location> locations = new ArrayList<>(letterTilesMap.keySet());
        if(locations.size() == 7)
            result += 50;
        boolean isHorizontal = board.areCollinearInRow(locations);
        Pair<Location, Location> pair = board.getFirstAndLastElements(locations, isHorizontal);
        Location location = pair.getKey();
        if(!location.equals(pair.getValue()))
            result += calculateScoreWord(location, locations, isHorizontal);
        while(board.squareAt(location) != null && board.squareAt(location).getLetterTile() != null){
            if(locations.contains(location)) {
                Location locationCross = new Location(location);
                while (board.squareAt(locationCross).getLetterTile() != null) {
                    if (isHorizontal)
                        locationCross = locationCross.up();
                    else
                        locationCross = locationCross.left();
                }
                if(board.squareAt(locationCross.down()) != null && board.squareAt(locationCross.down().down()) != null &&
                        board.squareAt(locationCross.down().down()).getLetterTile() != null && isHorizontal)
                    result += calculateScoreWord(locationCross.down(), locations, !isHorizontal);
                else if (board.squareAt(locationCross.right())!= null && board.squareAt(locationCross.right().right()) != null &&
                        board.squareAt(locationCross.right().right()).getLetterTile() != null && !isHorizontal)
                    result += calculateScoreWord(locationCross.right(), locations, !isHorizontal);
            }
            if(isHorizontal)
                location = location.right();
            else
                location = location.down();
        }
        return result;
    }

    /**
     * Calculate the score of a word
     * @param location location
     * @param locations List of all locations played in this term
     * @param isHorizontal horizontal or vertical
     * @return score
     */
    private int calculateScoreWord(Location location, List<Location> locations, boolean isHorizontal) {
        int result = 0;
        List<Integer> wordMultipliers = new ArrayList<>();
        StringBuilder word = new StringBuilder();
        while(board.letterTileAt(location) != null){
            Square square = board.squareAt(location);
            int letterPoints = square.getLetterTile().points();
            int multiplier = square.squareType().multiplier();
            switch (square.squareType().multiplierType()){
                case LETTERMULTIPLIER:
                    if(locations.contains(location))
                        result += multiplier * letterPoints;
                    else
                        result += letterPoints;
                    break;
                case WORDMULTIPLIER: result += letterPoints;
                    if(locations.contains(location))
                        wordMultipliers.add(multiplier);
                    break;
                default: throw new IllegalArgumentException("Can be only letter or word multiplier");
            }
            word.append(square.getLetterTile().letter());
            if(isHorizontal)
                location = location.right();
            else
                location = location.down();
        }
        lastMoveWords.add(word.toString());
        for(Integer multiplier: wordMultipliers)
            result *= multiplier;
        return result;
    }

    /**
     * Checks if the move is valid
     * @param letterTilesMap map of letter tiles and location
     * @return true is valid, false otherwise
     */
    private boolean isMoveValid(Map<Location, LetterTile> letterTilesMap) {
        List<Location> locations = new ArrayList<Location>(letterTilesMap.keySet());
        boolean emptyOrAdjacentValidation;
        if(board.isEmpty())
            emptyOrAdjacentValidation = board.anyTileAtCenter(locations) && locations.size() > 1;
        else
            emptyOrAdjacentValidation = board.anyExistingTileAdjacent(locations);
        return areTilesOnPlayersRack(letterTilesMap.values())   &&
               board.areValidLocations(locations)               &&
               board.areCollinearAndContinuous(locations)       &&
               emptyOrAdjacentValidation;
    }

    /**
     * Checks if collection of tiles are their on the rack of the current player
     * @param letterTiles
     * @return true if yes, false otherwise
     */
    private boolean areTilesOnPlayersRack(Collection<LetterTile> letterTiles) {
        return currentPlayer().getRack().containsAll(letterTiles);
    }

    /**
     * Pass move from the user
     */
    public void pass() {
        scorelessTurnCount++;
        resetLastMoves();
        checkGameEnd();
        progressTurn();
    }

    /**
     * Exchanges the tile with new tiles from the bag
     * @param letterTilesMap Map of letter tiles and location on the rack
     * @return true if tiles were exchanged, false otherwise
     */
    public boolean exchangeTiles(Map<Integer, LetterTile> letterTilesMap) {
        List<LetterTile> exchangedLetterTiles = bag.exchangeTiles(new ArrayList<LetterTile>(letterTilesMap.values()));
        if(exchangedLetterTiles.size() == 0) return false;
        List<LetterTile> rack = currentPlayer().getRack();
        int i = 0;
        for(Integer tileLocation: letterTilesMap.keySet()) {
            rack.set(tileLocation, exchangedLetterTiles.get(i++));
        }
        currentPlayer().setRack(rack);
        scorelessTurnCount++;
        resetLastMoves();
        checkGameEnd();
        progressTurn();
        return true;
    }

    /**
     * Challenges the last move of the player
     * if challenge is successful then player who challenged the move looses the turn
     * else, the game is restored the state before the move was made
     * @param player player who challenges the move
     */
    public void challenge(Player player) {
        if(lastMovePlayerIndex< 0 || lastMovePlayerIndex > players.size() - 1)
            throw new IllegalArgumentException("No move to challenge");
        if(player.equals(players.get(lastMovePlayerIndex))) {
            notifyMessage("You can't challenge your own word");
            return;
        }
        if(!lastMoveWords.isEmpty()) {
            List<String> words = new ArrayList<>();
            lastMoveWords.stream().parallel().map((item) -> item.toLowerCase())
                    .collect(Collectors.toCollection(() -> words));
            if (dictionary.containsAll(words)) {
                notifyMessage("Challenge Failed. Found word in dictionary");
                int turnLooses = turnLossMap.get(player);
                turnLossMap.remove(player);
                turnLossMap.put(player, turnLooses + 1);
                if(player.equals(whoseTurn))
                    progressTurn();
            } else {
                notifyMessage("Challenge Successful");
                scorelessTurnCount++;
                changeBoard(lastBoard);
                players.get(lastMovePlayerIndex).addOrSubtractScore(lastMoveScore , !add);
                players.get(lastMovePlayerIndex).setRack(new ArrayList<>(lastMovePlayerRack));
            }
        }
        resetLastMoves();
        notifyPlayerChanged();
    }

    /**
     * Check if the game has ended
     * Conditions
     * 1. All racks are empty and the player who has the turn has empty rack
     * 2. 6 consecutive scoreless turns have been played (includes pass, successful challenge and exchange)
     */
    private void checkGameEnd() {
        boolean sixConsecutiveScorelessTurns = (scorelessTurnCount == 6? true:false);
        isGameEnd = (bag.isEmpty() && whoseTurn.getRack().isEmpty()) || sixConsecutiveScorelessTurns;
        if(isGameEnd) {
            for(Player player: players) {
                for(LetterTile tile: player.getRack())
                    player.addOrSubtractScore(tile.points(), add);
            }
            notifyGameEnded();
        }
    }

    /**
     * Tell whether the game has ended or not
     * @return true if game has ended, false otherwise
     */
    private void notifyGameEnded() {
        for (GameChangeListener listener : gameChangeListeners) {
            listener.gameEnded(winner());
        }
    }

    /**
     * Returns the winner with maximum score
     * @return player winner
     */
    private Player winner() {
        int max = 0;
        Player winner = null;
        for(Player player: players){
            if(player.score() > max) {
                max = player.score();
                winner = player;
            }
        }
        return winner;
    }

    /**
     * Returns all the player in the game
     * @return list of players
     */
    public List<Player> getPlayers() {
        return new ArrayList<>(players);
    }

    /**
     * Change the current board with another board
     * @param board instance of another board
     */
    void changeBoard(Board board) {
        notifyLetterTilesRemoved(this.board.getLetterTileMap());
        this.board = board;
        notifyLetterTilesPlaced(board.getLetterTileMap());
    }

    /**
     * Notify the listeners about the letter tiles placed
     * @param letterTileMap letter tiles and locations
     */
    private void notifyLetterTilesPlaced(Map<Location, LetterTile> letterTileMap) {
        for (GameChangeListener listener : gameChangeListeners) {
            for (Map.Entry<Location, LetterTile> entry : letterTileMap.entrySet()) {
                listener.letterTilePlaced(entry.getKey(), entry.getValue());
            }
        }
    }

    /**
     * Notify the listeners that special tiles has been placed
     * @param location location of the tile
     * @param specialTile the special tile
     */
    private void notifySpecialTilesPlaced(Location location, SpecialTile specialTile) {
        for (GameChangeListener listener : gameChangeListeners) {
                listener.specialTilePlaced(location, specialTile);
        }
    }

    /**
     * Notify the listener about special tile removed
     * @param location location of removed tile
     * @param player owner of the tile
     */
    void notifySpecialTilesRemoved(Location location, Player player) {
        for (GameChangeListener listener : gameChangeListeners) {
            listener.specialTileRemoved(location, player);
        }
    }

    /**
     * Notify the listeners about the remove letter tiles
     * @param letterTileMap removed letter tiles
     */
    void notifyLetterTilesRemoved(Map<Location, LetterTile> letterTileMap) {
        for (GameChangeListener listener : gameChangeListeners) {
            for (Map.Entry<Location, LetterTile> entry : letterTileMap.entrySet()) {
                listener.letterTileRemoved(entry.getKey(), entry.getValue());
            }
        }
    }

    /**
     * Notifier to send the updated from the game
     * @param message string message
     */
    void notifyMessage(String message) {
        for (GameChangeListener listener : gameChangeListeners) {
            listener.messageReceived(message);
        }
    }

    /**
     * Notify the change of player
     */
    private void notifyPlayerChanged() {
        for (GameChangeListener listener : gameChangeListeners) {
            listener.currentPlayerChanged(currentPlayer());
        }
    }
}
