import java.lang.reflect.*;
import java.util.*;

/**
 * Represents a brokerage.
 * 
 * @author Janani Asokumar
 * @version 3/20/2020
 * @author 3
 */
public class Brokerage
    implements Login
{
    private Map<String, Trader> traders;
    private Set<Trader>         loggedTraders;
    private StockExchange       exchange;

    /**
     * Constructs new brokerage affiliated with a given stock exchange.
     * Initializes the map of traders to an empty map (a TreeMap), keyed by
     * trader's name; initializes the set of active (logged-in) traders to an
     * empty set (a TreeSet).
     * 
     * @param exchange
     *            - a stock exchange
     */
    public Brokerage(StockExchange exchange)
    {
        this.exchange = exchange;
        traders = new TreeMap<String, Trader>();
        loggedTraders = new TreeSet<Trader>();

    }


    /**
     * Tries to register a new trader with a given screen name and password. If
     * successful, creates a Trader object for this trader and adds this trader
     * to the map of all traders (using the screen name as the key).
     * 
     * @param name
     *            - screen name of the trader
     * @param password
     *            - password of the trader
     * @return int - 0 if successful, or an error code (a negative integer) if
     *         failed
     */
    public int addUser(String name, String password)
    {
        if (name.length() < 4 || name.length() > 10)
        {
            return -1;
        }
        else if (password.length() < 2 || password.length() > 10)
        {
            return -2;
        }
        else if (traders.containsKey(name))
        {
            return -3;
        }

        Trader n = new Trader(this, name, password);
        traders.put(name, n);
        return 0;
    }


    /**
     * Requests a quote for a given stock from the stock exchange and passes it
     * along to the trader by calling trader's receive Message method.
     * 
     * @param symbol
     *            - the stock symbol
     * @param trader
     *            - the trader who requested a quote
     */
    public void getQuote(String symbol, Trader trader)
    {
        trader.receiveMessage(exchange.getQuote(symbol));
    }


    /**
     * Tries to login a trader with a given screen name and password. If no
     * messages are waiting for the trader, sends a "Welcome to SafeTrade!"
     * message to the trader. Opens a dialog window for the trader by calling
     * trader's openWindow() method. Adds the trader to the set of all logged-in
     * traders.
     * 
     * @param name
     *            - screen name of the trader
     * @param password
     *            - password of the trader
     * @return int - 0 if worked, negative number if failed
     */
    public int login(String name, String password)
    {
        if (!traders.containsKey(name))
        {
            return -1;
        }
        else if (!traders.get(name).getPassword().equals(password))
        {
            return -2;
        }
        else if (loggedTraders.contains(traders.get(name)))
        {
            return -3;
        }

        Trader l = traders.get(name);

        if (!l.hasMessages())
        {
            l.receiveMessage("Welcome to SafeTrade!");
        }
        loggedTraders.add(l);
        l.openWindow();
        return 0;
    }


    /**
     * Removes a specified trader from the set of logged-in traders. The trader
     * may be assumed to logged in already.
     * 
     * @param trader
     *            - the trader that logs-out
     */
    public void logout(Trader trader)
    {
        loggedTraders.remove(trader);
    }


    /**
     * Places an order at the stock exchange.
     * 
     * @param order
     *            - an order to be placed at the stock exchange.
     */
    public void placeOrder(TradeOrder order)
    {
        exchange.placeOrder(order);
    }


    //
    // The following are for test purposes only
    //
    /**
     * returns the map of traders
     * 
     * @return Traders - the map of traders
     */
    protected Map<String, Trader> getTraders()
    {
        return traders;
    }


    /**
     * returns set of logged Traders
     * 
     * @return set - returns set of logged in traders
     */
    protected Set<Trader> getLoggedTraders()
    {
        return loggedTraders;
    }


    /**
     * returns the stockExchange
     * 
     * @return StockExhange - the exchange
     */
    protected StockExchange getExchange()
    {
        return exchange;
    }


    /**
     * <p>
     * A generic toString implementation that uses reflection to print names and
     * values of all fields <em>declared in this class</em>. Note that
     * superclass fields are left out of this implementation.
     * </p>
     * 
     * @return a string representation of this Brokerage.
     */
    public String toString()
    {
        String str = this.getClass().getName() + "[";
        String separator = "";

        Field[] fields = this.getClass().getDeclaredFields();

        for (Field field : fields)
        {
            try
            {
                str += separator + field.getType().getName() + 
                    " " + field.getName() + ":"
                    + field.get(this);
            }
            catch (IllegalAccessException ex)
            {
                System.out.println(ex);
            }

            separator = ", ";
        }

        return str + "]";
    }
    
//    public static void main(String[] args) {
//        StockExchange e = new StockExchange();
//        
//        Brokerage brok = new Brokerage(e);
//        
//        System.out.println(brok.toString());
//    }
}
