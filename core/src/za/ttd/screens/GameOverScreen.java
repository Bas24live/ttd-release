package za.ttd.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ai.msg.MessageManager;
import com.badlogic.gdx.ai.msg.Telegram;
import com.badlogic.gdx.ai.msg.TelegramProvider;
import com.badlogic.gdx.ai.msg.Telegraph;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import za.ttd.characters.states.MessageType;
import za.ttd.game.Game;

public class GameOverScreen extends AbstractScreen implements Telegraph, TelegramProvider {

    private Stage stage = new Stage();
    private Table table = new Table();
    private Skin skin = new Skin(Gdx.files.internal("defaultui/uiskin.json"));
    private Label gameOverLabel = new Label("GAME OVER!", skin);
    private TextButton buttonViewHighScores = new TextButton("High Scores", skin);
    private TextButton buttonViewPlayerStatistics = new TextButton("Statistics", skin);
    private TextButton buttonReturnToMainMenu = new TextButton("Main Menu", skin);
    private TextButton buttonTryAgain = new TextButton("Try Again", skin);
    private ScreenController screenController;
    private Game game;

    public GameOverScreen() {
        registerSelfAsProvider();
        game = Game.getInstance();
        screenController = ScreenController.getInstance();
    }
    private void registerSelfAsProvider() {
        MessageManager.getInstance().addProviders(this,
                MessageType.LOAD_LEVEL);
    }

    public void show() {
        buttonTryAgain.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.newGame();
            }
        });
        buttonViewHighScores.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Game.getInstance().setScreen(new LoadingScreen(
                        "Loading Highscores ...",
                        new Runnable() {
                            @Override
                            public void run() {
                                MessageManager.getInstance().dispatchMessage(
                                        Game.getInstance(),
                                        MessageType.UPDATE_DB);
                            }
                        }, ScreenTypes.HIGH_SCORES
                ));
            }
        });

        buttonViewPlayerStatistics.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ScreenController.getInstance().setScreen(ScreenTypes.PLAYER_STATS);
            }
        });
        buttonReturnToMainMenu.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Game.getInstance().setScreen(new LoadingScreen(
                        "Updating database...",
                        new Runnable() {
                            @Override
                            public void run() {
                                MessageManager.getInstance().dispatchMessage(
                                        Game.getInstance(),
                                        MessageType.UPDATE_DB);
                            }
                        },
                        ScreenTypes.MAIN_MENU
                ));
            }
        });

        table.add(gameOverLabel).height(50).padBottom(20).center().row();
        table.add(buttonTryAgain).size(150, 40).padBottom(20).row();
        table.add(buttonViewHighScores).size(150, 40).padBottom(20).row();
        table.add(buttonViewPlayerStatistics).size(150, 40).padBottom(20).row();
        table.add(buttonReturnToMainMenu).size(150, 40).padBottom(20).row();
        table.setFillParent(true);
        stage.addActor(table);
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act();
        stage.draw();
    }
    @Override
    public void hide() {
        dispose();
    }


    @Override
    public void dispose() {
        stage.dispose();
        skin.dispose();
    }

    @Override
    public boolean handleMessage(Telegram msg) {
        return false;
    }

    @Override
    public Object provideMessageInfo(int msg, Telegraph receiver) {
        return null;
    }
}
