package by.epam.javawebtraining.kukareko.horseracebet.dao.builder;

/**
 * @author Yulya Kukareko
 * @version 1.0 15 Apr 2019
 */
public class FactoryBuilder {

    public static AbstractBuilder getBuilder(TypeBuilder type) {
        AbstractBuilder builder = null;

        switch (type) {
            case BET:
                builder = BetBuilder.getInstance();
                break;
            case HORSE:
                builder = HorseBuilder.getInstance();
                break;
            case HORSE_STARTING_PRICE:
                builder = HorseStartingPriceBuilder.getInstance();
                break;
            case RACE:
                builder = RaceBuilder.getInstance();
                break;
            case RESULT:
                builder = ResultBuilder.getInstance();
                break;
            case USER_BET:
                builder = UserBetBuilder.getInstance();
                break;
            case USER:
                builder = UserBuilder.getInstance();
                break;
        }
        return builder;
    }
}
