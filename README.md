# Dine-N-Dash

## Overview

Dine-N-Dash is an app designed to make splitting the bill at restaurants easy.

It uses the [Firebase Text Recongnition API](https://firebase.google.com/docs/ml-kit/recognize-text) to construct a list of items from an image of a receipt.

Users can then assign those receipt items to people in their contacts and send them an SMS requesting payment via [PayPal.me](https://www.paypal.me/)

In addition, Dine-N-Dash uses a [Firebase Realtime Database](https://firebase.google.com/docs/database/) on the backend to keep a history of past transactions

## Architecture

Dine-N-Dash was built on the Model View View-Model (MVVM) architecture.

It makes use of many of the new [AndroidX](https://developer.android.com/jetpack/androidx/) libraries, and closesly follows Google's [reccomended app architecture](https://developer.android.com/jetpack/docs/guide).

### Creators:
[Ryan Filkins](https://github.com/ryanfilkins97), [Shelby Heffron](https://github.com/smheffron), [Brian Lasker](https://github.com/blasker97), [Jeff Rohlman](https://github.com/jeffrohlman), and [Clayton Cornett](https://github.com/ClaytonCornett)
