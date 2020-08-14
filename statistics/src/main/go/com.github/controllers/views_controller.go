package controllers

import (
	"../dto"
	"../services"
	"../utils"
)

type ViewsController struct {
	service *services.ViewService
}

func NewViewsController(service *services.ViewService) *ViewsController {
	return &ViewsController{service: service}
}

// FindByUserId godoc
// @Summary Get details of user views
// @Description Get details of user views
// @Tags views
// @Accept  json
// @Produce  json
// @Param userId path integer true "User ID"
// @Success 200 {object} dto.ViewDto
// @Router /v1/views/fetch/{userId} [get]
func (controller *ViewsController) FindByUserId(userId uint) ([]*dto.ViewDto, error) {
	views, err := controller.service.ReadByUserId(userId)
	if err != nil {
		return nil, err
	}
	return utils.FromViewsArray(views), nil
}

// FindViews godoc
// @Summary Get details of user views
// @Description Get details of user views
// @Tags views
// @Accept  json
// @Produce  json
// @Param Authorization header string true "Bearer"
// @Success 200 {object} dto.ViewDto
// @Router /v1/views [get]
func (controller *ViewsController) FindViews(userId uint) ([]*dto.ViewDto, error) {
	views, err := controller.service.ReadByUserId(userId)
	if err != nil {
		return nil, err
	}
	return utils.FromViewsArray(views), nil
}

// CreateView godoc
// @Summary Create a new views
// @Description Create a new views
// @Tags views
// @Accept  json
// @Produce  json
// @Param view body dto.ViewDto true "Create view"
// @Success 200 {object} dto.ViewDto
// @Router /v1/views [post]
// @Param Authorization header string true "Bearer"
func (controller *ViewsController) CreateView(userId uint, payload *dto.ViewDto) (*dto.ViewDto, error) {
	views := utils.ToView(userId, payload)
	result, err := controller.service.CreateView(views)
	if err != nil {
		return nil, err
	}
	return utils.FromView(result), nil
}