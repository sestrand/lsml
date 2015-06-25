package org.lisoft.lsml.command;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.lisoft.lsml.model.chassi.ComponentBase;
import org.lisoft.lsml.model.chassi.Location;
import org.lisoft.lsml.model.item.Internal;
import org.lisoft.lsml.model.item.Item;
import org.lisoft.lsml.model.item.ItemDB;
import org.lisoft.lsml.model.loadout.LoadoutBase;
import org.lisoft.lsml.model.loadout.component.ConfiguredComponentBase;
import org.lisoft.lsml.model.upgrades.UpgradeDB;
import org.lisoft.lsml.model.upgrades.Upgrades;
import org.lisoft.lsml.util.message.MessageXBar;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class OpRemoveItemTest {
    @Mock
    private ConfiguredComponentBase loadoutPart;
    @Mock
    private LoadoutBase<?>          loadout;
    @Mock
    private Upgrades                upgrades;
    @Mock
    private MessageXBar             xBar;
    @Mock
    private ComponentBase           internalPart;

    @Before
    public void setup() {
        Mockito.when(loadout.getUpgrades()).thenReturn(upgrades);
        Mockito.when(loadoutPart.getInternalComponent()).thenReturn(internalPart);
        Mockito.when(internalPart.getLocation()).thenReturn(Location.CenterTorso);
    }

    @Test
    public void testDescription() {
        Item item = ItemDB.ECM;

        OpRemoveItem cut = new OpRemoveItem(xBar, loadout, loadoutPart, item);

        assertTrue(cut.describe().contains("remove"));
        assertTrue(cut.describe().contains("from"));
        assertTrue(cut.describe().contains(loadoutPart.getInternalComponent().getLocation().toString()));
        assertTrue(cut.describe().contains(item.getName()));
    }

    @Test
    public void testDescription_artemis() {
        Item item = ItemDB.lookup("LRM 20");
        Mockito.when(upgrades.getGuidance()).thenReturn(UpgradeDB.ARTEMIS_IV);

        OpRemoveItem cut = new OpRemoveItem(xBar, loadout, loadoutPart, item);

        assertTrue(cut.describe().contains("remove"));
        assertTrue(cut.describe().contains("from"));
        assertTrue(cut.describe().contains(loadoutPart.getInternalComponent().getLocation().toString()));
        assertTrue(cut.describe().contains(item.getName()));
    }

    /**
     * If an item can't be removed, an exception shall be thrown when the operation is applied.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testCantRemoveItem() {
        OpRemoveItem cut = null;
        try {
            Item item = ItemDB.lookup("LRM 20");
            Mockito.when(loadoutPart.getItemsEquipped()).thenReturn(new ArrayList<Item>());
            cut = new OpRemoveItem(xBar, loadout, loadoutPart, item);
        }
        catch (Throwable t) {
            fail("Setup failed");
            return;
        }

        cut.apply();
    }

    /**
     * Internal items can't be removed. Shall throw directly on creation.
     */
    @SuppressWarnings("unused")
    // Expecting exception
    @Test(expected = IllegalArgumentException.class)
    public void testCantRemoveInternal() {
        Internal item = Mockito.mock(Internal.class);
        new OpRemoveItem(xBar, loadout, loadoutPart, item);
    }
}