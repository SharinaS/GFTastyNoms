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
* CloudFormation
  * Automates deployment of S3 and IAM resources
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

# Lessons learned
### Error: `Resource is not in the state stackUpdateComplete`

# Debugging Issues and Steps
## Upon Trying to Add Storage and Auth together via CLI
### `The runtime parameter of nodejs8.10 is no longer supported for creating or updating AWS Lambda functions`
* Updated node with homebrew
* changed node version to 12.x in
  * amplify/backend/auth/gftastynomsd65cba56/gftastynomsd65cba56-cloudformation-template.yml
  * amplify/backend/awscloudformation/nested-cloudformation-stack.yml -- this file, however, gets rewritten everytime amplify push is run, so nodejs reverts back to 8.10. Solution - update command line?

### `Resource is not in the state stackUpdateComplete`
* Updated Node
* Updated Amplify CLI - `npm install -g @aws-amplify/cli`
* Updated NPM when terminal prompted it

### `Cannot read property 'awscloudformation' of undefined`
* Updated API key - `amplify update api`
* To see all environments: `amplify env list [–details] [–json]`
* Removed environment - `amplify env remove gfnomsenv`
* `npm install -g @aws-amplify/cli`
* Created new environment - `amplify env add`
* `amplify add api`, but was then informed by the terminal to write, `amplify update api`, at which point I followed the instructions that followed.


### Some of helpful resources used for debugging
* https://aws-amplify.github.io/docs/cli-toolchain/quickstart#environments--teams
* https://github.com/aws-amplify/amplify-cli/issues/1269
* https://github.com/aws-amplify/amplify-cli
* https://aws-amplify.github.io/docs/sdk/android/start