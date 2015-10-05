package za.ttd.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ai.msg.MessageManager;
import com.badlogic.gdx.ai.msg.Telegram;
import com.badlogic.gdx.ai.msg.Telegraph;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.Json;
import za.ttd.characters.states.MessageType;
import za.ttd.database.ConnectDB;
import za.ttd.screens.*;

public class Game extends com.badlogic.gdx.Game
        implements Telegraph
{
    private Level level;
    private Player player;
    private Assets assets;
    private boolean newPlayer;

    private Json json = new Json();
	public static final String TITLE = "The Wrath of Thomas the Dentist";
	public static final int WIDTH = 600, HEIGHT = 800;

    public static Game instance = null;
    private ScreenController screenController;

    private Game() {
        registerSelfAsListener();
        ConnectDB.getInstance();
        newPlayer = true;
    }

    public static Game getInstance() {
        if(instance != null)
            return instance;
        return instance = new Game();
    }

	@Override
	public void create() {
		screenController = ScreenController.getInstance();
        screenController.setScreen(ScreenTypes.SPLASH);
        assets = Assets.getInstance();
        assets.Load();
	}

    public void newGame() {
        player.setLives(3);
        player.setHighestLevel(1);
        createGame();
    }

    //Creates a new game depending on the players level
    public void createGame() {
        setLevel(new Level(player));
        screenController.setScreen(ScreenTypes.GAME);
    }

    public Level getLevel() {
        return level;
    }

    private void gameOver() {
        player.setLives(3);
        player.setHighestLevel(1);
        setScreen(new LoadingScreen(
                "Updating database...",
                () -> {
                    MessageManager.getInstance().dispatchMessage(this,
                            MessageType.UPDATE_DB);
                },
                ScreenTypes.MAIN_MENU
        ));
    }

    private void registerSelfAsListener() {
        MessageManager.getInstance().addListeners(this,
                MessageType.GAME_OVER,
                MessageType.NEXT_LEVEL
        );
    }

    @Override
    public boolean handleMessage(Telegram msg) {
        switch (msg.message) {
            case MessageType.GAME_OVER:
                gameOver();
                return true;
            case MessageType.NEXT_LEVEL:
                player.incHighestLevel();
                createGame();
                return true;
        }
        return false;
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Player loadPlayer(String playerName) {
        Json json = new Json();
        Player object = null;
        FileHandle fin = Gdx.files.internal(String.format(
                "core/assets/data/%s.player",
                playerName
        ));
        try {
            String data = fin.readString();
            object = json.fromJson(Player.class, data);
        } catch (GdxRuntimeException e) {
            Gdx.app.log("GAME", e.getMessage());
        }
        return object;
    }

    public void savePlayer() {
        FileHandle fout = Gdx.files.internal(String.format(
                "core/assets/data/%s.player",
                player.getName()
        ));
        Json json = new Json();
        String data = json.prettyPrint(player);
        fout.writeString(data, false);
    }

    public int getPlayerID() {
        return this.player.getPlayerID();
    }

    public Player getPlayer() {
        return player;
    }

    public boolean isNewPlayer() {
        return newPlayer;
    }

    public void setNewPlayer(boolean newPlayer) {
        this.newPlayer = newPlayer;
    }
}
