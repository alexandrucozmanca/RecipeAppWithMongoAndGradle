package ro.alex.learning.RecipeApplication.converters;

import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import ro.alex.learning.RecipeApplication.command.CategoryCommand;
import ro.alex.learning.RecipeApplication.domain.Category;


@Component
public class CategoryToCategoryCommand implements Converter<Category, CategoryCommand> {

    @Synchronized
    @Nullable
    @Override
    public CategoryCommand convert(Category category) {
        if (category == null)
            return null;

        CategoryCommand categoryCommand = new CategoryCommand();
        categoryCommand.setDescription(category.getDescription());
        categoryCommand.setId(category.getId());

        return categoryCommand;
    }
}
