enum Direction {Longitude, Latitude}

public class CoordinateLB {
    private Direction direction;
    private Integer degrees;
    private Integer minutes;
    private Integer seconds;

    CoordinateLB(Direction direct){
        setDirection(direct);
        setDefaultDegrees();
        setDefaultMinutes();
        setDefaultSeconds();
    }
    CoordinateLB(Direction direct, Integer degr) throws IllegalArgumentException{
        setDirection(direct);
        if(!setDegrees(degr))
            throw new IllegalArgumentException("Check parameter for degrees");
        setDefaultMinutes();
        setDefaultSeconds();
    }
    CoordinateLB(Direction direct, Integer degr, Integer min) throws IllegalArgumentException{
        setDirection(direct);
        if(!setDegrees(degr) || !setMinutes(min))
            throw new IllegalArgumentException("Check parameter for degrees and minutes");
        setDefaultSeconds();
    }
    CoordinateLB(Direction direct, Integer degr, Integer min, Integer sec) throws IllegalArgumentException{
        setDirection(direct);
        if(!(setDegrees(degr) && setMinutes(min) && setSeconds(sec)))
            throw new IllegalArgumentException("Check parameter for degrees, minutes and seconds");
    }

    public Direction getDirection(){
        return direction;
    }
    public void setDirection(Direction dir){
        direction = dir;
    }

    public Integer getDegrees(){
        return degrees;
    }
    public boolean setDegrees(Integer deg){
        if(direction.equals(Direction.Latitude) && -90 <= deg && deg <= 90 ) {
            degrees = deg;
            return true;
        } else if(direction.equals(Direction.Longitude) && -180 <= deg && deg <= 180 ) {
            degrees = deg;
            return true;
        }

        return false;
    }
    public void setDefaultDegrees(){
        degrees = 0;
    }

    public Integer getMinutes(){
        return minutes;
    }
    public boolean setMinutes(Integer m){
        if(m >= 0  && m < 60) {
            minutes = m;
            return true;
        }
        return false;
    }
    public void setDefaultMinutes(){
        minutes = 0;
    }

    public Integer getSeconds(){
        return seconds;
    }
    public boolean setSeconds(Integer s){
        if(s >= 0 && s < 60) {
            seconds = s;
            return true;
        }
        return false;
    }
    public void setDefaultSeconds(){
        seconds = 0;
    }

    public String toString(){
        String directionLetter = directionLetter();
        if(directionLetter == null)
            return null;
        return degrees + "°" + minutes + "′" + seconds + "\" " + directionLetter;
    }
    public String toStringDec(){
        String directionLetter = directionLetter();
        Double decCoord = degrees + minutes/60.0 + seconds/3600.0;
        return decCoord + "°" + directionLetter;
    }
    public CoordinateLB middleWith(CoordinateLB coord){
        return middleBetween(this, coord);
    }

    private CoordinateLB middleBetween(CoordinateLB c1, CoordinateLB c2){
        if(!c1.getDirection().equals(c2.getDirection()))
            return null;
        Integer resDegr = (c1.getDegrees() + c2.getDegrees())/2;
        Integer resMin = (c1.getMinutes() + c2.getMinutes())/2;
        if((c1.getDegrees() + c2.getDegrees()) % 2 == 1)
            resMin += 30;
        Integer resSec = (c1.getSeconds() + c2.getSeconds())/2;
        if((c1.getMinutes() + c2.getMinutes()) % 2 == 1)
            resSec += 30;
        return new CoordinateLB(c1.getDirection(), resDegr, resMin, resSec);
    }

    private String directionLetter(){
        if(direction.equals(Direction.Latitude)){
            if(degrees > 0)
                return"N\"";
            else if(degrees < 0)
                return"S\"";
            else if(minutes > 0)
                return"N\"";
            else if(minutes < 0)
                return"S\"";
            else if(seconds > 0)
                return"N\"";
            else if(seconds < 0)
                return"S\"";
            return "";
        } else if(direction.equals(Direction.Longitude)){
            if(degrees > 0)
                return"E\"";
            else if(degrees < 0)
                return"W\"";
            else if(minutes > 0)
                return"E\"";
            else if(minutes < 0)
                return"W\"";
            else if(seconds > 0)
                return"E\"";
            else if(seconds < 0)
                return"W\"";
            return "";
        }
        return null;
    }
}
