package practicalpractice3.category;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
// 商品分类
public class Category {
    private String cateId;
    private String cateName;
    private String cateDesc;

    @Override
    public String toString() {
        return "Category{" +
                "cateDesc='" + cateDesc + '\'' +
                ", cateId='" + cateId + '\'' +
                ", cateName='" + cateName + '\'' +
                '}';
    }
}