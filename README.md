# GFTastyNoms

# Contributor
* Sharina Stubbs

# Technology And Languages Used
* Java
* Android Studio
* File Picker
* AWS Services
  * Amplify Framework 2.0
  * API, GraphQL, DynamoDB

## Amplify
AWS Services Added Automatically During Amplify Setup:
* S3 Bucket
* IAM
* CloudWatch
* AWS AppSync

## S3
Storage uses:
* TransferUtility - a high-level wrapper over Amazon S3 client
* Cognito - required to add storage for user files
  * Hint - when adding a friendly name for the user - choose `default`
  * Hint - when providing a bucket name, write your own (do not choose `default`)
  * Hint - when choosing who has access, I chose `Auth and guest users`
  * Hint - when choosing kind of access for Authenticated and Guest users, choose `create/update`, `read`, `delete`
  * Hint - I chose not to have a lambda trigger.
  * Hint - in the terminal, write `amplify push` when done.

### Resources
* [Android SDK S3 docs](https://aws-amplify.github.io/docs/sdk/android/storage), version 2.0, which provide step-by-step instructions on storing and retrieving user files from cloud storage.

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

# App Functionality
* User is able to view various pages to the app
  * home
  * All GF eateries currently in the DB
  * A Page that allows the addition of a GF eatery to the DB
  * Ability to add an image associated with a GF location from their phone.

# Stretch Goals
* Put data inputted into DynamoDB into a local Room DB
