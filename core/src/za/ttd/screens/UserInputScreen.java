package za.ttd.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import za.ttd.ttd;

/**
 * @author minnaar
 * @since 2015/09/24.
 */
public class UserInputScreen extends AbstractScreen {
    private Stage stage = new Stage();
    private Table table = new Table();
    private Skin skin = new Skin(Gdx.files.internal("core/assets/skins/menuSkin.json"));
    private Label labelName = new Label("Player Name", skin);
    private TextField textName = new TextField("Name...", skin);
    private TextButton buttonContinue = new TextButton("Continue", skin);
    private Dialog dialog = new Dialog("Info", skin);

    public UserInputScreen(ttd game) {
        super(game);
    }

    @Override
    public void show() {
        table.add(labelName).row();
        table.add(textName).row();
        table.add(buttonContinue).row();
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
