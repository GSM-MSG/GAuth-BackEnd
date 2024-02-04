package main

import (
	"bytes"
	"encoding/json"
	"io"
	"net/http"
	"os"
)

type User struct {
	Email    string `json:"email"`
	Password string `json:"password"`
}

type Token struct {
	AccessToken string `json:"accessToken"`
}

func main() {
	client := &http.Client{}

	url := os.Getenv("GAUTH_SERVER_URL")

	user := User{
		Email:    os.Getenv("GAUTH_USER_NAME"),
		Password: os.Getenv("GAUTH_PASSWORD"),
	}

	reqBody, _ := json.Marshal(user)

	req, err := http.NewRequest("POST", url, bytes.NewBuffer(reqBody))
	req.Header.Set("Content-Type", "application/json")

	res, err := client.Do(req)
	if err != nil {
		panic(err)
	}

	defer res.Body.Close()

	body, err := io.ReadAll(res.Body)
	if err != nil {
		panic(err)
	}

	var token Token
	err = json.Unmarshal(body, &token)
	if err != nil {
		panic(err)
	}

	err = os.WriteFile("../jwt.txt", []byte(token.AccessToken), 0777)
	if err != nil {
		panic(err)
	}
}
