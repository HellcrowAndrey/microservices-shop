package repository

import (
	"../entity"
	"github.com/jinzhu/gorm"
)

const (
	ProductColumn        = "Products"
	CustomerColumn       = "Customer"
	ProductsImagesColumn = "Products.Images"
)

type PurchaseRepo struct {
	database *gorm.DB
}

func NewPurchaseRepo(database *gorm.DB) *PurchaseRepo {
	return &PurchaseRepo{database: database}
}

func (repo *PurchaseRepo) FindByUserId(userId uint) ([]*entity.Purchase, error) {
	var data []*entity.Purchase
	err := repo.database.
		Preload(ProductColumn).
		Preload(CustomerColumn).
		Preload(ProductsImagesColumn).
		Order("created_at").
		Where("user_id = ?", userId).
		Find(&data).Error
	return data, err
}

func (repo *PurchaseRepo) Save(purchase *entity.Purchase) (*entity.Purchase, error) {
	tx := repo.database.Begin()
	err := tx.Save(&purchase).Error
	if err != nil {
		tx.Rollback()
		return nil, err
	}
	tx.Commit()
	return purchase, nil
}