package practicalpractice2.category;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
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