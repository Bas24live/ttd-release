package za.ttd.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import za.ttd.ttd;

/**
 * @author minnaar
 * @since 18 July 2015
 *
 * The main menu that contains buttons to sub-menus and other actions.
 *
 */
public class MainMenu extends AbstractScreen {
    private Stage stage = new Stage();
    private Table table = new Table();
    private Skin skin = new Skin(Gdx.files.internal("core/assets/skins/menuSkin.json"));
    private TextButton buttonContinue = new TextButton("Continue", skin);
    private TextButton buttonNewGame = new TextButton("New Game", skin);
    private TextButton buttonSavedGames = new TextButton("Saved Games", skin);
    private TextButton buttonStatistics = new TextButton("Statistics", skin);
    private TextButton buttonControls = new TextButton("Controls", skin);
    private TextButton buttonCredits = new TextButton("Credits", skin);
    private TextButton buttonExit = new TextButton("Exit", skin);
    private Label title = new Label("Main Menu", skin);

    private ttd game;

    public MainMenu(ttd game) {
        super(game);
        this.game = game;

    }

    @Override
    public void show() {
        buttonContinue.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                //game.setScreen(new GameScreen(game));
                game.continueGame("Bas");
            }
        });

        buttonNewGame.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.newGame("Bas");
            }
        });

        buttonExit.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });

        table.add(title).padBottom(40).row();
        table.add(buttonContinue).size(150,60).padBottom(20).row();
        table.add(buttonNewGame).size(150,60).padBottom(20).row();
        table.add(buttonSavedGames).size(150,60).padBottom(20).row();
        table.add(buttonStatistics).size(150,60).padBottom(20).row();
        table.add(buttonControls).size(150,60).padBottom(20).row();
        table.add(buttonCredits).size(150,60).padBottom(20).row();
        table.add(buttonExit).size(150,60).padBottom(20).row();

        table.setFillParent(true);
        stage.addActor(table);

        Gdx.input.setInputProcessor(stage);
    }




    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act();
        stage.draw();
    }

    @Override
    public void dispose() {
        stage.dispose();
        skin.dispose();
    }
}
