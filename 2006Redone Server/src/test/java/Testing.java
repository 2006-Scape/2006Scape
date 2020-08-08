import com.google.gson.Gson;
import com.rebotted.game.npcs.NpcHandler;
import com.rebotted.game.npcs.drops.NPCDropsHandler;
import com.rebotted.util.NpcDrop;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;

public class Testing {

    public static void getItemDrops() throws IOException {
        ArrayList<NpcDrop> npcDrops = new ArrayList<>();
        Writer             writer   = new FileWriter("npcdrops-dump.json");

        for (int i = 0; i <= NpcHandler.MAX_NPCS; i++) {
            npcDrops.add(new NpcDrop(i, NPCDropsHandler.getNpcDrops("", i)));
        }

        new Gson().toJson(npcDrops, writer);

        writer.close();
    }

    public static void main(String[] args) throws IOException {
        new NPCDropsHandler();

        //getItemDrops();
        /*Map<String, ItemDrop[]> test     = getItemDrops();
        ArrayList<NpcDrop>      allItems = new ArrayList<>();

        Iterator it = test.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();

            String   name     = (String) pair.getKey();
            ItemDrop[] itemDrop = (ItemDrop[]) pair.getValue();

            System.out.println(name);
            allItems.add(new NpcDrop(name, itemDrop));

            it.remove(); // avoids a ConcurrentModificationException
        }

        String json = new Gson().toJson(allItems);
        System.out.println(json);*/
    }
}
