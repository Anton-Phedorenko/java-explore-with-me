package ru.practicum.admin_access.categories.service.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.admin_access.categories.dto.CategoryDto;
import ru.practicum.admin_access.categories.mapper.CategoryMapper;
import ru.practicum.admin_access.categories.model.Category;
import ru.practicum.admin_access.categories.repository.CategoryRepository;
import ru.practicum.admin_access.categories.service.dal.CategoryService;
import ru.practicum.exceptions.exception.ConstraintForeignKeyException;
import ru.practicum.exceptions.exception.NotFoundException;
import ru.practicum.private_access.events.repository.EventRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final EventRepository eventRepository;

    @Transactional
    @Override
    public CategoryDto create(CategoryDto categoryDto) {
        return CategoryMapper.toCategoryDto(categoryRepository.save(CategoryMapper.toCategory(categoryDto)));
    }

    @Transactional
    @Override
    public CategoryDto update(Long id, CategoryDto newCategoryDto) {
        Category category = getById(id);
        category.setName(newCategoryDto.getName());

        return CategoryMapper.toCategoryDto(category);
    }

    @Transactional
    @Override
    public void delete(Long id) {
        if (eventRepository.existsByCategory(getById(id))) {
            throw new ConstraintForeignKeyException("The category is not empty");
        }

        categoryRepository.deleteById(id);
    }

    @Override
    public Category getById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String
                        .format("Category with id=%s was not found", id)));
    }

    @Override
    public List<CategoryDto> getByParam(Integer from, Integer size) {
        return categoryRepository.findAll(PageRequest.of(from > 0 ? from / size : 0, size))
                .map(CategoryMapper::toCategoryDto)
                .stream().collect(Collectors.toList());
    }
}

