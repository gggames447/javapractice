package practicalpractice2.goods;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Goods {
    private String goodId;
    private String goodName;
    private double goodPrice;
    private int goodStock;
    private String cateId;

    @Override
    public String toString() {
        return "Goods{" +
                "cateId='" + cateId + '\'' +
                ", goodId='" + goodId + '\'' +
                ", goodName='" + goodName + '\'' +
                ", goodPrice='" + goodPrice + '\'' +
                ", goodStock='" + goodStock + '\'' +
                '}';
    }
}