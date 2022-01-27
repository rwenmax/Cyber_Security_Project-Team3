<div id="top"></div>
<div align="center">
    <h1 align= "center">CSPTeam3</h1>
</div>
<details>
  <summary>Table of Contents</summary>
  <ol>
    <li>
      <a href="#about-the-project">About The Project</a>
    </li>
    <li><a href="#checklist">Checklist</a></li>
    <li>
      <a href="#Entities">Entities</a>
	    <ul>
        <li><a href="#token">Token</a></li>
      </ul>
      <ul>
        <li><a href="#profile">Track</a></li>
      </ul>
	    <ul>
        <li><a href="#profileitem">Customer</a></li>
      </ul>
    </li>
      <li><a href="#tools-and-Frameworks">Tools and Frameworks</a></li>
	<li><a href="#credits-and-contact">Credits and Contact</a></li>
	  <li><a href="#license">License</a></li>
  </ol>
</details>

## About The Project

>Create a Database that holds information such as Profiles and their usernames, passwords and what apps, components and packages they want. As well as a table that holds all the possible apps, components and packages that can be used.
>
>Keep in mind when coding of SOLID principles, design patterns, Java standard practices, git conflicts when merging, JDBC configuration and OOP. 
>
>Create an API that allows to manipulate previously mentioned database for CRUD operations such as creation, deletion, reading and updating.

## Checklist

- [x] SOLID principles
- [x] Java standard practices
- [x] Use of git and sorting merging conflicts
- [x] Use of JDBC
- [x] Entities, Repositories and Controllers
- [x] Junit Testing

## Functions

This section demonstrates how to use requests for given entity. 

Functions that Update, Add, Delete or Get sensitive information such as Customer or Employee Data require an additional post-fix of "/{token}", in that post-fix a token is inserted for authentication.

### **Token**

#### *GET all tokens*

URL endpoint: cyberteam3/tokens

Response: list of all tokens 

```json
[
    {
        "id": 1,
        "token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbmRyZXdAY2hpbm9va2NvcnAuY29tIn0.IB8oVEAMZs-7sW8Yrqgj_oOj8bM1piDfAU9ho42YWEg",
        "user_id": "1",
    },
    {
        "id": 2,
        "token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbmRyZXdAY2hpbm9va2NvcnAuY29tIn0.IB8oVEAMZs-7sW8Yrqgj_oOj8bM1piDfAU9ho42YWEg",
        "user_id": "2",
    },
]
```

#### *GET token by Token*

URL endpoint: /cyberteam3/token/findbytoken/{token}

Response: token object for given token

Example for token with token: "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbmRyZXdAY2hpbm9va2NvcnAuY29tIn0.IB8oVEAMZs-7sW8Yrqgj_oOj8bM1piDfAU9ho42YWEg"

```json
{
    "id": 1,
    "token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbmRyZXdAY2hpbm9va2NvcnAuY29tIn0.IB8oVEAMZs-7sW8Yrqgj_oOj8bM1piDfAU9ho42YWEg",
    "user_id": "1",
}
```

#### Create token

URL endpoint: /cyberteam3/token/add/{user_id}

Response: Generates a new token for the specified user


#### DELETE token by token

URL endpoint: /cyberteam3/token/deletebytoken/{token}

Response: deletes token row from the tokens table based on the given token

#### DELETE token by token id

URL endpoint: /cyberteam3/token/deletebyid/{token}

Response: deletes token row from the tokens table based on the given token id

<p align="right">(<a href="#top">back to top</a>)</p>

### **Profile**

#### *GET all profiles*

URL endpoint: cyberteam3/users

Response: list of all profiles

```json
[
    {
        "id": 1,
        "token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbmRyZXdAY2hpbm9va2NvcnAuY29tIn0.IB8oVEAMZs-7sW8Yrqgj_oOj8bM1piDfAU9ho42YWEg",
        "user_id": "1",
    },
    {
        "id": 2,
        "token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbmRyZXdAY2hpbm9va2NvcnAuY29tIn0.IB8oVEAMZs-7sW8Yrqgj_oOj8bM1piDfAU9ho42YWEg",
        "user_id": "2",
    },
]
```

#### *GET token by Token*

URL endpoint: /cyberteam3/token/findbytoken/{token}

Response: token object for given token

Example for token with token: "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbmRyZXdAY2hpbm9va2NvcnAuY29tIn0.IB8oVEAMZs-7sW8Yrqgj_oOj8bM1piDfAU9ho42YWEg"

```json
{
    "id": 1,
    "token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbmRyZXdAY2hpbm9va2NvcnAuY29tIn0.IB8oVEAMZs-7sW8Yrqgj_oOj8bM1piDfAU9ho42YWEg",
    "user_id": "1",
}
```

#### Create token

URL endpoint: /cyberteam3/token/add/{user_id}

Response: Generates a new token for the specified user


#### DELETE token by token

URL endpoint: /cyberteam3/token/deletebytoken/{token}

Response: deletes token row from the tokens table based on the given token

#### DELETE token by token id

URL endpoint: /cyberteam3/token/deletebyid/{token}

Response: deletes token row from the tokens table based on the given token id

<p align="right">(<a href="#top">back to top</a>)</p>
