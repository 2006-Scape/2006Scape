package plugin.npc.shopkeeper;

import com.rs2.game.dialog.Dialogue;
import com.rs2.game.dialog.DialogueFactory;


public final class GeneralStoreDialogue extends Dialogue {
    @Override
    public void sendDialogues(DialogueFactory factory) {
        factory.getPlayer().dialoguePlugin = true;
        factory.sendNpcChat("Can I help you at all?")
                .sendOption("Yes please. What are you selling?", () -> {
                    factory.onAction(() -> {
                        factory.getPlayer().getShopAssistant().openShop(88);
                    });
                }, "No thanks.", () -> {
                    factory.sendPlayerChat("No thanks.");
                }).execute();
    }

}

