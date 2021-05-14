/* ensure the page has correct title */
describe(`Page title`, () => {
  it('has correct title', () => {
    cy.visit('/accounts/login')
    cy.title().should('eq', 'Login')
  })
})

/* ensure all links point to the correct pages  */
describe(`Links contents`, () => {
  before(() => {
    cy.visit('/accounts/login')
  })

  it(`"Forgot your?"`, () => {
    cy.get('#link-password-change').should(
      'have.attr',
      'href',
      '/accounts/password/change'
    )
  })

  it(`"Don't have?"`, () => {
    cy.get('#link-register').should('have.attr', 'href', '/accounts/register')
  })
})

describe(`Links directs`, () => {
  beforeEach(() => {
    cy.visit('/accounts/login')
  })

  it(`"Forgot your?"`, () => {
    cy.get('#link-password-change').click()
    cy.url().should(
      'eq',
      Cypress.config().baseUrl + '/accounts/password/change'
    )
  })

  it(`"Don't have?"`, () => {
    cy.get('#link-register').click()
    cy.url().should('eq', Cypress.config().baseUrl + '/accounts/register')
  })
})
