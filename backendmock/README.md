# Backend Mock
A Mock for customer and accounts based on `json-server`.
## Set up
Clone this repo and, within its folder, run `npm install` and then `npm start`.

It will set up a server at [http://localhost:3000]()

The port can be specified by editing `json-server.json`:
```
{
  "port": 3000
}```

## Usage Examples

[http://localhost:3000/customers]() will output:
```
[
  {
    "id": 1,
    "handle": "@gvisoc",
    "name": "Gabriel Viso"
  },
  {
    "id": 2,
    "handle": "@onemorebug",
    "name": "Oscar Serna"
  }
]```
[http://localhost:3000/customers/2]() will output:
```
{
  "id": 2,
  "handle": "@onemorebug",
  "name": "Oscar Serna"
}```
[http://localhost:3000/customers/1/accounts]() will output:
```
[
  {
    "id": 1,
    "name": "current",
    "balance": "5,234.56 AUD",
    "customerId": 1
  },
  {
    "id": 2,
    "name": "savings",
    "balance": "4,321.00 AUD",
    "customerId": 1
  },
  {
    "id": 3,
    "name": "savings",
    "balance": "1,746.00 AUD",
    "customerId": 1
  }
]```

It supports filters: [http://localhost:3000/customers/1/accounts?name=current]() will output:
```
[
  {
    "id": 1,
    "name": "current",
    "balance": "5,234.56 AUD",
    "customerId": 1
  }
]```

**The accounts are queried separatedly**.

[http://localhost:3000/accounts]() will output:
```
[
  {
    "id": 1,
    "name": "current",
    "balance": "5,234.56 AUD",
    "customerId": 1
  },
  {
    "id": 2,
    "name": "savings",
    "balance": "4,321.00 AUD",
    "customerId": 1
  },
  {
    "id": 3,
    "name": "savings",
    "balance": "1,746.00 AUD",
    "customerId": 1
  },
  {
    "id": 4,
    "name": "dayToDay",
    "balance": "4,635.90 AUD",
    "customerId": 2
  },
  {
    "id": 5,
    "name": "savING",
    "balance": "8,360.22 AUD",
    "customerId": 2
  },
  {
    "id": 6,
    "name": "super",
    "balance": "14,560.22 AUD",
    "customerId": 2
  }
]```
[http://localhost:3000/accounts/5]() will output:
```
{
  "id": 5,
  "name": "savING",
  "balance": "8,360.22 AUD",
  "customerId": 2
}```
