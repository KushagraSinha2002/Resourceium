/* ensure the page has correct title */
describe(`Page title`, () => {
  it('has correct title', () => {
    cy.visit('/')
    cy.title().should('eq', 'Resourceium')
  })
})

/* ensure all links point to the correct pages  */
describe(`Links contents`, () => {
  before(() => {
    cy.visit('/')
  })

  it(`"Why Resourceium?"`, () => {
    cy.get('#link-faq').should('have.attr', 'href', '/faq')
  })

  it(`"Sign In"`, () => {
    cy.get('#link-login').should('have.attr', 'href', '/accounts/login')
  })

  it(`"Sign Up"`, () => {
    cy.get('#link-register').should('have.attr', 'href', '/accounts/register')
  })

  it(`"Sign Up for Resourceium"`, () => {
    cy.get('#link-register-button').should(
      'have.attr',
      'href',
      '/accounts/register'
    )
  })
})

describe(`Links directs`, () => {
  beforeEach(() => {
    cy.visit('/')
  })

  it(`"Why Resourceium?"`, () => {
    cy.get('#link-faq').click()
    cy.url().should('eq', Cypress.config().baseUrl + '/faq')
  })

  it(`"Sign In"`, () => {
    cy.get('#link-login').click()
    cy.url().should('eq', Cypress.config().baseUrl + '/accounts/login')
  })

  it(`"Sign Up"`, () => {
    cy.get('#link-register').click()
    cy.url().should('eq', Cypress.config().baseUrl + '/accounts/register')
  })

  it(`"Sign Up for Resourceium"`, () => {
    cy.get('#link-register-button').click()
    cy.url().should('eq', Cypress.config().baseUrl + '/accounts/register')
  })
})
