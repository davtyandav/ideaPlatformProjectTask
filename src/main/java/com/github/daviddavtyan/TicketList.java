package com.github.daviddavtyan;

import java.util.ArrayList;
import java.util.List;

public class TicketList {

    private List<Ticket> tickets =new ArrayList<>();

    public List<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(List<Ticket> ticketList) {
        this.tickets = ticketList;
    }

}

