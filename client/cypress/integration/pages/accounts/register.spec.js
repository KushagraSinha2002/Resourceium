/* ensure the page has correct title */
describe(`Page title`, () => {
  it('has correct title', () => {
    cy.visit('/accounts/register')
    cy.title().should('eq', 'Register')
  })
})

/* ensure all links point to the correct pages  */
describe(`Links contents`, () => {
  before(() => {
    cy.visit('/accounts/register')
  })

  it(`"Already have?"`, () => {
    cy.get('#link-login').should('have.attr', 'href', '/accounts/login')
  })
})

/* ensure all links redirect to the correct page  */
describe(`Links directs`, () => {
  beforeEach(() => {
    cy.visit('/accounts/register')
  })

  it(`"Already have?"`, () => {
    cy.get('#link-login').click()
    cy.url().should('eq', Cypress.config().baseUrl + '/accounts/login')
  })
})
