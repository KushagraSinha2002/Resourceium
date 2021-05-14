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
})

describe(`Links directs`, () => {
  beforeEach(() => {
    cy.visit('/accounts/register')
  })
})
