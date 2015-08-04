package ishpal.hungrystillmustwait;

/**
 * Created by Ishpal on 1/8/2015.
 */
public class Restaurant {

    private String _name, _address;
    public int _totalWaitingTime, _numUsers, _averageWaitingTime, _currentWaitingTime;

    public Restaurant(String name, int totalWaitingTime, int numUsers, int averageWaitingTime, int currentWaitingTime, String address) {
        _name = name;
        _totalWaitingTime = totalWaitingTime;
        _numUsers = numUsers;
        _averageWaitingTime = averageWaitingTime;
        _currentWaitingTime = currentWaitingTime;
        _address = address;
    }

    public Restaurant(String name) {
        _name = name;
        _totalWaitingTime = 0;
        _numUsers = 0;
        _averageWaitingTime = 0;
        _currentWaitingTime = 0;
        _address = "";
    }

    public String getName() {
        return _name;
    }

    public int getAverageWaitingTime() {
        return _averageWaitingTime;
    }

    public int getNumUsers() {
        return _numUsers;
    }

    public int getTotalWaitingTime() {
        return _totalWaitingTime;
    }

    public int getCurrentWaitingTime() {
        return _currentWaitingTime;
    }

    public String getAddress(){ return _address;}

    public void setAverageWaitingTime(int num) {
        _averageWaitingTime = num;
    }

    public void setNumUsers(int num) {
        _numUsers = num;
    }

    public void setTotalWaitingTime(int num) {
        _totalWaitingTime = num;
    }

    public void setCurrentWaitingTime(int num) {
        _currentWaitingTime = num;
    }
}
