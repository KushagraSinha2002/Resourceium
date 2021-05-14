const DESCRIPTION = 'Testing URL -->'

describe(`${DESCRIPTION} "/"`, () => {
  beforeEach(() => {
    cy.visit('/')
  })

  it('has correct title', () => {
    cy.title().should('eq', 'Resourceium')
  })
})

describe(`${DESCRIPTION} "/accounts/login"`, () => {
  beforeEach(() => {
    cy.visit('/accounts/login')
  })

  it('has correct title', () => {
    cy.title().should('eq', 'Login')
  })
})
