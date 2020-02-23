# GFTastyNoms

# Contributor
* Sharina Stubbs

# Technology And Languages Used
* Java
* Android Studio
* AWS Services

## AWS Services Used
* Amplify Framework 2.0

### AWS Services Automatically Added via Amplify
* S3 Bucket
* IAM
* CloudWatch
* AWS AppSync

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