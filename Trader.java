import java.lang.reflect.*;
import java.util.*;

/**
 * Represents a stock trader.
 * @author Janani Asokumar
 * @version 3/23/2021
 */

public class Trader
    implements Comparable<Trader>
{
    private Brokerage     brokerage;
    private String        screenName;
    private String        password;
    private TraderWindow  myWindow;
    private Queue<String> mailbox;

    /**
     * Constructs a new trader, affiliated with a given brokerage, with a given
     * screen name and password.
     * 
     * @param brokerage
     *            - brokerage for this trader
     * @param screenName
     *            - user name
     * @param password
     *            - password
     */
    public Trader(Brokerage brokerage, String screenName, String password)
    {
        this.brokerage = brokerage;
        this.screenName = screenName;
        this.password = password;
        mailbox = new PriorityQueue<String>();

    }


    /**
     * Compares this trader to another by comparing their screen names case
     * blind.
     * 
     * @param other
     *            - the reference to a trader with which to compare.
     * @return int - the result of the comparison of this trader and other.
     */
    public int compareTo(Trader other)
    {
        return this.getName().compareToIgnoreCase(other.getName());
    }


    /**
     * Indicates whether some other trader is "equal to" this one, based on
     * comparing their screen names case blind.
     * 
     * @param other 
     *         - the reference to an object with which to compare.
     * @return - true if this trader's screen name is the same as other's;false
     *         otherwise.
     */
    public boolean equals(Object other)
    {
        if (other instanceof Trader)
        {
            return this.getName().equalsIgnoreCase(((Trader)other).getName());
        }
        throw new ClassCastException();
        

    }


    /**
     * Returns the screen name for this trader.
     * 
     * @return String - the screen name of this trader
     */
    public String getName()
    {
        return screenName;
    }


    /**
     * Returns the password for this trader.
     * 
     * @return - the password of this trader
     */
    public String getPassword()
    {
        return password;
    }


    /**
     * Requests a quote for a given stock symbol from the brokerage by
     * callingbrokerage's getQuote.
     * 
     * @param symbol
     *            - a stock symbol for which a quote is requested.
     */
    public void getQuote(String symbol)
    {
        brokerage.getQuote(symbol, this);
    }


    /**
     * Returns true if this trader has any messages in its mailbox.
     * 
     * @return boolean - if the trader has any messages in their mailbox
     */
    public boolean hasMessages()
    {
        return !mailbox.isEmpty();
    }


    /**
     * Creates a new TraderWindow for this trader and saves a reference to it in
     * myWindow.
     */
    public void openWindow()
    {
        myWindow = new TraderWindow(this);
        while (!mailbox.isEmpty())
        {
            myWindow.showMessage(mailbox.remove());
        }

    }


    /**
     * Places a given order with the brokerage by calling brokerage's
     * placeOrder.
     * 
     * @param order
     *            - a trading order to place
     */
    public void placeOrder(TradeOrder order)
    {
        brokerage.placeOrder(order);
    }


    /**
     * Logs out this trader.
     */
    public void quit()
    {
        brokerage.logout(this);
        myWindow = null;
    }


    /**
     * Adds msg to this trader's mailbox and displays all messages.
     * 
     * @param msg
     *            - a message to be added to this trader's mailbox
     */
    public void receiveMessage(String msg)
    {
        mailbox.add(msg);
        if (myWindow != null)
        {
            while (!mailbox.isEmpty())
            {
                myWindow.showMessage(mailbox.remove());
            }
        }

    }


    //
    // The following are for test purposes only
    //
    /**
     * return mailbox
     * @return the mailbox
     */
    protected Queue<String> mailbox()
    {
        return mailbox;
    }


    /**
     * <p>
     * A generic toString implementation that uses reflection to print names and
     * values of all fields <em>declared in this class</em>. Note that
     * superclass fields are left out of this implementation.
     * </p>
     * 
     * @return a string representation of this Trader.
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
                if (field.getType().getName().equals("Brokerage"))
                {
                    str += separator + field.getType().getName() 
                        + " " + field.getName();
                }
                else
                {
                    str += separator + field.getType().getName() + " " 
                        + field.getName() + ":" + field.get(this);
                }
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
//        StockExchange se = new StockExchange();
//        Brokerage b = new Brokerage(se);
//        Trader t = new Trader(b, "Bob", "123");
//        System.out.println(t.toString());
//    }
}


