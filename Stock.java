import java.util.*;
import java.lang.reflect.*;
//import java.text.DecimalFormat;

/**
 * Represents a stock in the SafeTrade project
 * 
 * @author Janani Asokumar
 * @version 3/20/2021
 */
public class Stock
{
    //private static DecimalFormat      money = new DecimalFormat("0.00");

    private String                    stockSymbol;
    private String                    companyName;
    private double                    loPrice;
    private double                    hiPrice;
    private double                    lastPrice;
    private int                       volume;
    private PriorityQueue<TradeOrder> buyOrders;
    private PriorityQueue<TradeOrder> sellOrders;

    /**
     * Constructs a new stock with a given symbol, company name,and starting
     * price.
     * 
     * @param symbol
     *            - the symbol of the company
     * @param name
     *            - the name of the company
     * @param price
     *            - the price
     */
    public Stock(String symbol, String name, double price)
    {
        stockSymbol = symbol;
        companyName = name;
        loPrice = price;
        hiPrice = price;
        lastPrice = price;
        volume = 0;
        sellOrders = new PriorityQueue<TradeOrder>(
            10, new PriceComparator(true));
        buyOrders = new PriorityQueue<TradeOrder>(
            10, new PriceComparator(false));
    }


    /**
     * Executes as many pending orders as possible.
     */
    protected void executeOrders()
    {
        boolean stuck = false;
        while (!stuck && !buyOrders.isEmpty() && !sellOrders.isEmpty())
        {
            TradeOrder b = buyOrders.peek();
            TradeOrder s = sellOrders.peek();
            double p = 0.0;
            int minShares;

            if (b.isLimit() && s.isLimit())
            {
                if (b.getPrice() < s.getPrice())
                {
                    stuck = true;
                    break;
                }
                else
                {
                    p = s.getPrice();
                }
            }
            else if (b.isLimit() && s.isMarket())
            {
                p = b.getPrice();
            }
            else if (b.isMarket() && s.isLimit())
            {
                p = s.getPrice();
            }
            else if (b.isMarket() && s.isMarket())
            {
               
                p = lastPrice;
            }

            if (s.getShares() < b.getShares())
            {
                minShares = s.getShares();
            }
            else
            {
                minShares = b.getShares();
            }

            s.subtractShares(minShares);
            b.subtractShares(minShares);
            if (s.getShares() == 0)
            {
                sellOrders.remove();
            }
            if (b.getShares() == 0)
            {
                buyOrders.remove();
            }

            if (p > hiPrice)
            {
                hiPrice = p;
            }
            else if (p < loPrice)
            {
                loPrice = p;
            }
            lastPrice = p;

            volume += minShares;
            String message = minShares + " " + s.getSymbol() 
                + " at " + String.format("%.2f", p)
                + " amt " + String.format("%.2f", minShares * p);

            s.getTrader().receiveMessage("You sold: " + message);
            b.getTrader().receiveMessage("You bought: " + message);
            
        }

    }


    /**
     * Returns a quote string for this stock.
     * 
     * @return - quote string
     */
    public String getQuote()
    {
        String q = companyName + " (" + stockSymbol + ") " + "\nPrice: "
            + String.format("%.2f", lastPrice) 
            + " hi: " + String.format("%.2f", hiPrice) + " lo: "
            + String.format("%.2f", loPrice) + " " + "vol: " + volume;
        if (sellOrders.isEmpty())
        {
            q += " \nAsk: none";
        }
        else
        {
            q += " \nAsk: " + String.format(
                "%.2f", sellOrders.peek().getPrice()) 
                + " size: " + sellOrders.peek().getShares();

        }
        if (buyOrders.isEmpty())
        {
            q += " Bid: none";
        }
        else
        {
            q += " Bid: " + String.format(
                "%.2f", buyOrders.peek().getPrice()) 
                + " size: " + buyOrders.peek().getShares();

        }
        // System.out.println(q);
        return q;

    }


    /**
     * Places a trading order for this stock
     * 
     * @param order
     *            - a trading order to be placed with this stock exchange
     */
    public void placeOrder(TradeOrder order)
    {
        String q = "";
        if (order.isBuy())
        {
            buyOrders.add(order);
            q = "New order: Buy " + order.getSymbol() 
                + " (" + companyName + ") " + "\n"
                + order.getShares() + " shares at ";

        }
        else if (order.isSell())
        {
            sellOrders.add(order);
            q = "New order: Sell " + order.getSymbol() 
                + " (" + companyName + ") " + "\n"
                + order.getShares() + " shares at ";

        }

        if (order.isMarket())
        {
            q += "market";

        }
        else
        {
            q += "$" + String.format("%.2f", order.getPrice());
        }
        order.getTrader().receiveMessage(q);

        this.executeOrders();
    }

    //
    // The following are for test purposes only
    //


    /**
     * return the stock symbol
     * 
     * @return the stock symbol
     */
    protected String getStockSymbol()
    {
        return stockSymbol;
    }


    /**
     * return the company name
     * 
     * @return company name
     */
    protected String getCompanyName()
    {
        return companyName;
    }


    /**
     * return the low price
     * 
     * @return low price
     */
    protected double getLoPrice()
    {
        return loPrice;
    }


    /**
     * return high price
     * 
     * @return high price
     */
    protected double getHiPrice()
    {
        return hiPrice;
    }


    /**
     * return the last price
     * 
     * @return last price
     */
    protected double getLastPrice()
    {
        return lastPrice;
    }


    /**
     * return the volume
     * 
     * @return the volume
     */
    protected int getVolume()
    {
        return volume;
    }


    /**
     * return the buyOrders
     * 
     * @return the buyOrders
     */
    protected PriorityQueue<TradeOrder> getBuyOrders()
    {
        return buyOrders;
    }


    /**
     * the sell orders
     * 
     * @return the sell orders
     */
    protected PriorityQueue<TradeOrder> getSellOrders()
    {
        return sellOrders;
    }


    /**
     * <p>
     * A generic toString implementation that uses reflection to print names and
     * values of all fields <em>declared in this class</em>. Note that
     * superclass fields are left out of this implementation.
     * </p>
     * 
     * @return a string representation of this Stock.
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
                str += separator + field.getType().getName() 
                    + " " + field.getName() + ":"
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
//       System.out.println(String.format("%.2f", 10.0)); 
//    }
}
