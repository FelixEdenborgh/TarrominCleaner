package MainClass;

import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.map.Area;
import org.dreambot.api.script.AbstractScript;
import org.dreambot.api.script.Category;
import org.dreambot.api.script.ScriptManifest;
import org.dreambot.api.wrappers.interactive.NPC;
import org.dreambot.api.wrappers.items.Item;

import java.awt.*;


@ScriptManifest(author = "Feed", category = Category.HERBLORE, name = "Tarromin Cleaner", version = 1.0)
public class MainClass extends AbstractScript {

    Area bankArea = new Area(3159, 3493, 3170, 3484, 0);
    int cleanOrder[] = {0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27};

    @Override
    public void onStart() {
        log("Starting");
    }

    @Override
    public int onLoop() {
        if(!getInventory().isFull()){
            if(bankArea.contains(getLocalPlayer())){
                NPC banker = getNpcs().closest(npc -> npc != null && npc.hasAction("Bank"));
                if(banker.interact("Bank")){
                    if(sleepUntil(() -> getBank().isOpen(), 2000)){
                        if(sleepUntil(() ->getInventory().isFull(), 2000)){
                            getBank().depositAllExcept(item -> item != null && item.getName().contains("Grimy tarromin")); //make so it will deposit all if you have something there
                        }
                        if(sleepUntil(() -> !getInventory().isFull(), 2000)){
                            getBank().withdrawAll("Grimy tarromin");
                            if(sleepUntil(() -> getInventory().isFull(), 2000)){
                                if(getBank().close()){
                                    sleepUntil(() -> !getBank().isOpen(), 2000);
                                    sleep(Calculations.random(300, 3500));
                                    getInventory().interact("Grimy tarromin", "Clean");
                                    for(int i : cleanOrder){
                                        Item item = getInventory().getItemInSlot(i);
                                        getInventory().interact("Grimy tarromin", "Clean");
                                        sleep(300, 700);

                                    }
                                }
                            }
                        }

                    }
                }
            }
        }
        else if(getInventory().isFull()){
            if (bankArea.contains(getLocalPlayer())){
                NPC banker = getNpcs().closest(npc -> npc != null && npc.hasAction("Bank"));
                if (banker.interact("Bank")){
                    if(sleepUntil(() -> getBank().isOpen(), 2000)){
                        if(sleepUntil(() ->getInventory().isFull(), 1000)){
                            getBank().depositAllItems();
                            if(getBank().close()){
                                sleepUntil(() -> !getBank().isOpen(), 5000);
                                return Calculations.random(500, 1000);
                            }
                        }
                    }
                }
            }
        }
        return 0;
    }

    @Override
    public void onExit() {
        super.onExit();
    }

    @Override
    public void onPaint(Graphics graphics){
        super.onPaint(graphics);
    }


}
