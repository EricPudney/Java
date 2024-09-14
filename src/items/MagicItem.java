package src.items;

import java.util.Random;

import src.characters.Hero;

public interface MagicItem {
        public void applyBuff(Hero player);
        public void removeBuff(Hero player);
        public static Random rng = new Random();

}
