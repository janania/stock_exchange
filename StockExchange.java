import java.lang.reflect.*;
import java.util.*;

/**
 * Represents a stock exchange. A <code>StockExchange</code> keeps a
 * <code>HashMap</code> of stocks, keyed by a stock symbol. It has methods to
 * list a new stock, request a quote for a given stock symbol, and to place a
 * specified trade order.
 * @author Janani Asokumar
 * @version 3/23/2021
 */
public class StockExchange
{
    private Map<String, Stock> listedStocks;

    /**
     * Constructs a new stock exchange object.
     */
    public StockExchange()
    {
        listedStocks = new HashMap<String, Stock>();
    }


    /**
     * Returns a quote for a given stock.
     * 
     * @param symbol
     *            - stock symbol
     * @return quote for a given stock
     */
    public String getQuote(String symbol)
    {
        if (listedStocks.containsKey(symbol))
        {
            return listedStocks.get(symbol).getQuote();
        }
        return symbol + " not found.";
    }


    /**
     * Adds a new stock with given parameters to the listed stocks.
     * 
     * @param symbol
     *            - stock symbol
     * @param name
     *            - full company name
     * @param price
     *            - opening stock price
     */
    public void listStock(String symbol, String name, double price)
    {
        Stock s = new Stock(symbol, name, price);
        listedStocks.put(symbol, s);
    }


    /**
     * Places a trade order by calling stock. placeOrderfor the stock specified
     * by the stock symbol in the trade order.
     * 
     * @param order
     *            - a trading order to be placed with this stock exchange
     */
    public void placeOrder(TradeOrder order)
    {
        if (listedStocks.containsKey(order.getSymbol()))
        {
            listedStocks.get(order.getSymbol()).placeOrder(order);
        }
        else
        {
            order.getTrader().receiveMessage(order.getSymbol() + " not found");
        }

    }


    //
    // The following are for test purposes only
    //
    /**
     * returns the map of listed Stocks
     * @return the listed stocks
     */
    protected Map<String, Stock> getListedStocks()
    {
        return listedStocks;
    }


    /**
     * <p>
     * A generic toString implementation that uses reflection to print names and
     * values of all fields <em>declared in this class</em>. Note that
     * superclass fields are left out of this implementation.
     * </p>
     * 
     * @return a string representation of this StockExchange.
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
                str += separator + field.getType().getName() + " " 
                    + field.getName() + ":" + field.get(this);
            }
            catch (IllegalAccessException ex)
            {
                System.out.println(ex);
            }

            separator = ", ";
        }

        return str + "]";
    }
}
