# GFTastyNoms

# Contributor
* Sharina Stubbs

# Technology And Languages Used
* Java
* Android Studio
* AWS Services

## AWS Services Used
* Amplify Framework 2.0
* API, GraphQL, DynamoDB

### AWS Services added during Amplify Setup
* S3 Bucket
* IAM
* CloudWatch
* AWS AppSync

## App Functionality
* User is able to view various pages to the app
  * home
  * All GF eateries currently in the DB
  * A Page that allows the addition of a GF eatery to the DB
  * Ability to add an image associated with a GF location from their phone.

## DynamoDB
### Schema
```
type GFTastyNoms @model {
  id: ID!
  nomplacename: String!
  address: String
  gfmenu: Boolean
  dedicatedgf: Boolean
  gffriendlyrange: Int
}
```

# Stretch Goals
* Put data inputted into DynamoDB into a local Room DB
