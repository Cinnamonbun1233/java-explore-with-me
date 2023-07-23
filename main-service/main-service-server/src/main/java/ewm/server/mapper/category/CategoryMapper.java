package ewm.server.mapper.category;

import ewm.server.dto.category.CategoryDto;
import ewm.server.dto.category.NewCategoryDto;
import ewm.server.model.category.Category;

public class CategoryMapper {
    public static Category mapDtoToModel(NewCategoryDto newCategoryDto) {
        Category category = new Category();
        category.setName(newCategoryDto.getName());
        return category;
    }

    public static CategoryDto mapModelToDto(Category category) {
        return CategoryDto
                .builder()
                .id(category.getCategoryId())
                .name(category.getName())
                .build();
    }
}