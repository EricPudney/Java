package src.items;

import src.characters.Hero;

public interface MagicItem {
        void applyBuff(Hero player);
        void removeBuff(Hero player);
}
