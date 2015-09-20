package za.ttd.characters.states;

import com.badlogic.gdx.ai.fsm.State;
import com.badlogic.gdx.ai.msg.Telegram;
import za.ttd.characters.ToothDecay;

/**
 * @author minnaar
 * @since 2015/09/18.
 */
public enum ToothDecayState implements State<ToothDecay> {
    CHASE {
        @Override
        public void enter(ToothDecay toothDecay) {
            toothDecay.chase();
        }

        @Override
        public void update(ToothDecay toothDecay) {
            if (toothDecay.collided(toothDecay.getThomas().getPosition())) {
                toothDecay.killThomas();
            }
        }
    },
    FLEE {
        @Override
        public void enter(ToothDecay toothDecay) {
            toothDecay.flee();
        }

        @Override
        public void update(ToothDecay toothDecay) {
            if (toothDecay.collided(toothDecay.getThomas().getPosition())) {
                toothDecay.getToothDecayStateMachine().changeState(DIE);
            }
        }
    },
    DIE {
        @Override
        public void enter(ToothDecay toothDecay) {
            toothDecay.die();
        }

        @Override
        public void update(ToothDecay toothDecay) {
        }
    }
    ;

    @Override
    public void exit(ToothDecay toothDecay) {
    }

    @Override
    public boolean onMessage(ToothDecay toothDecay, Telegram telegram) {
        if (telegram.message == MessageType.TOOTHBRUSH_COLLECTED) {
            toothDecay.getToothDecayStateMachine().changeState(FLEE);
            return true;
        }
        else
            return false;
    }
}
