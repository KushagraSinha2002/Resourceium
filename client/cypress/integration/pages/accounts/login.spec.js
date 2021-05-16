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
    cy.get('#link-password-recover').should(
      'have.attr',
      'href',
      '/accounts/password/recover'
    )
  })

  it(`"Don't have?"`, () => {
    cy.get('#link-register').should('have.attr', 'href', '/accounts/register')
  })
})

/* ensure all links redirect to the correct page  */
describe(`Links directs`, () => {
  beforeEach(() => {
    cy.visit('/accounts/login')
  })

  it(`"Forgot your?"`, () => {
    cy.get('#link-password-recover').click()
    cy.url().should(
      'eq',
      Cypress.config().baseUrl + '/accounts/password/recover'
    )
  })

  it(`"Don't have?"`, () => {
    cy.get('#link-register').click()
    cy.url().should('eq', Cypress.config().baseUrl + '/accounts/register')
  })
})
