package src.items;

import src.characters.Hero;

public interface MagicItem {
        public void applyBuff(Hero player);
        public void removeBuff(Hero player);
}
