package models

type JwtToken struct {
	TokenType   string `json:"tokenType"`
	AccessToken string `json:"accessToken"`
}
