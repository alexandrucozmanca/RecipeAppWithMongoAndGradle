package ro.alex.learning.RecipeApplication.converters;

import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import ro.alex.learning.RecipeApplication.command.CategoryCommand;
import ro.alex.learning.RecipeApplication.domain.Category;

@Component
public class CategoryCommandToCategory implements Converter<CategoryCommand, Category> {

    @Synchronized
    @Nullable
    @Override
    public Category convert(CategoryCommand categoryCommand) {
        if (categoryCommand == null)
        return null;

        final Category category = new Category();
        category.setDescription(categoryCommand.getDescription());
        category.setId(categoryCommand.getId());

        return category;
    }
}
