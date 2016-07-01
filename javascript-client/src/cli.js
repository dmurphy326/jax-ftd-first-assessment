import net from 'net'
import vorpal from 'vorpal'

import { hash, compare } from './hashing'

const users = {}
const cli = vorpal()

cli.delimiter('auth:')

let server = net.createConnection(667, () => {
  const address = server.address()
  server.write(`connected to server ${address.address}:${address.port}`)
})

const register = cli.command('register <username> <password>')
register
  .description('Registers a user with this application')
  .action(function (args, cb) {
    return (
      Promise.resolve(users[args.username] !== undefined) // is user already registered?
        .then(
          (alreadyRegistered) =>
            alreadyRegistered
              ? this.log('Username already registered! Choose another one.')
              : hash(args.password)
                .then((hashedPassword) => users[args.username] = hashedPassword)
                .then(() => this.log('Registration successful!'))
        )
        .catch((err) => this.log(`An error occurred: ${err}`))
    )
  })

const login = cli.command('login <username> <password>')
login
  .description('Login with a specified username and password')
  .action(function (args, cb) {
    return (
      Promise.resolve(users[args.username])
        .then(
          (hashedPassword) =>
            hashedPassword === undefined
              ? this.log('Username or password incorrect. Try again!')
              : compare(args.password, hashedPassword)
                .then((correctPassword) =>
                  correctPassword
                    ? this.log('Successfully logged in!')
                    : this.log('Username or password incorrect. Try again!')
                )
        )
        .catch((err) => this.log(`An error occurred: ${err}`))
    )
  })

const ls = cli.command('ls')
ls
  .description('Displays the user/password object')
  .action(function (args, cb) {
    this.log(users)
    this.log(JSON.stringify(users))
    cb()
  })

export default cli
