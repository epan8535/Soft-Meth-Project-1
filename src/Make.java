/*
 * Make enum class that lists all the makes of the vehicles
 *
 * @author Jake Cordon and Everett Pan
 */


public enum Make {
    CHEVY,
    FORD,
    HONDA,
    TOYOTA;


    @Override
    public String toString(){
        return this.name();
    }



}
