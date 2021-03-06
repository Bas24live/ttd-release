package za.ttd.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ai.msg.MessageManager;
import com.badlogic.gdx.ai.msg.Telegram;
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

public class PauseMenuScreen extends AbstractScreen implements Telegraph{


    private Stage stage = new Stage();
    private Table table = new Table();
    private Skin skin = new Skin(Gdx.files.internal("defaultui/uiskin.json"));
    private TextButton btnContinue = new TextButton("Continue", skin);
    private TextButton btnControls = new TextButton("Controls", skin);
    private TextButton btnMainMenu = new TextButton("Main Menu", skin);
    private Label title = new Label("Pause Menu", skin);

    @Override
    public void show() {

        btnContinue.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ScreenController.getInstance().setScreen(ScreenTypes.GAME);
                MessageManager.getInstance().dispatchMessage(PauseMenuScreen.this, MessageType.LEVEL_STARTED);
            }
        });

        btnControls.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ScreenController.getInstance().setScreen(ScreenTypes.CONTROLS);
            }
        });

        btnMainMenu.addListener(new ClickListener() {
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

        table.add(title).padBottom(40).row();
        table.add(btnContinue).size(150, 40).padBottom(20).row();
        table.add(btnControls).size(150,40).padBottom(20).row();
        table.add(btnMainMenu).size(150,40).padBottom(20).row();

        table.setFillParent(true);
        stage.addActor(table);

        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act();
        stage.draw();
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
}
