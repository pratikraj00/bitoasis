/**
 * Entity class to store all the ticker btc-usd
 */

package com.bitoasisExample.bitoasis.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "TICKER_BTC_USD")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@Builder
@EntityListeners(AuditingEntityListener.class)
public class TickerBtcUsd extends AbstractAuditingEntity<String> implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long Id;

    @JsonProperty("BID")
    @Column(name = "BID")
    private float bid;

    @JsonProperty("BID_SIZE")
    @Column(name = "BID_SIZE")
    private float bidSize;

    @JsonProperty("ASK")
    private float ask;

    @JsonProperty("ASK_SIZE")
    private float askSize;

    @JsonProperty("DAILY_CHANGE")
    private float dailyChange;

    @JsonProperty("DAILY_CHANGE_RELATIVE")
    private float dailyChangeRelative;

    @JsonProperty("LAST_PRICE")
    private float lastPrice;

    @JsonProperty("VOLUME")
    private float volume;

    @JsonProperty("HIGH")
    private float high;

    @JsonProperty("LOW")
    private float low;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TickerBtcUsd that = (TickerBtcUsd) o;
        return Id == that.Id && Float.compare(that.bid, bid) == 0 && Float.compare(that.bidSize, bidSize) == 0 && Float.compare(that.ask, ask) == 0 && Float.compare(that.askSize, askSize) == 0 && Float.compare(that.dailyChange, dailyChange) == 0 && Float.compare(that.dailyChangeRelative, dailyChangeRelative) == 0 && Float.compare(that.lastPrice, lastPrice) == 0 && Float.compare(that.volume, volume) == 0 && Float.compare(that.high, high) == 0 && Float.compare(that.low, low) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(Id, bid, bidSize, ask, askSize, dailyChange, dailyChangeRelative, lastPrice, volume, high, low);
    }

    @Override
    public String toString() {
        return "TickerBtcUsd{" +
                "Id=" + Id +
                ", bid=" + bid +
                ", bidSize=" + bidSize +
                ", ask=" + ask +
                ", askSize=" + askSize +
                ", dailyChange=" + dailyChange +
                ", dailyChangeRelative=" + dailyChangeRelative +
                ", lastPrice=" + lastPrice +
                ", volume=" + volume +
                ", high=" + high +
                ", low=" + low +
                '}';
    }
}
