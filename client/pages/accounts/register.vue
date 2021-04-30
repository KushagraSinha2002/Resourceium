<template>
  <div
    class="container flex flex-wrap items-center min-h-screen mx-auto bg-cream-white place-content-center sm:place-content-evenly"
  >
    <div class="w-full sm:w-1/2">
      <img
        :src="require('~/assets/images/logo.png')"
        alt="Resourceium logo"
        class="h-32 mx-auto sm:h-80 md:h-96 xl:h-[30rem]"
      />
    </div>
    <div class="w-full px-6 sm:w-1/2 sm:px-0">
      <form
        class="flex flex-col font-poppins max-w-sm sm:max-w-[25rem] md:max-w-md mx-auto space-y-5 md:text-xl sm:text-lg"
        @submit.prevent="submitForm"
      >
        <div
          class="mt-10 mb-5 text-3xl font-bold sm:text-4xl sm:mb-10 font-poppins text-dark-black"
        >
          Please Register.
        </div>
        <div>
          <label for="username" class="hidden">Username</label>
          <input
            id="username"
            v-model="formData.username"
            type="text"
            placeholder="Username"
            class="w-full px-4 py-2 border rounded-lg appearance-none border-dark-black border-opacity-40 focus:outline-none focus:border-blue-300"
          />
        </div>
        <div class="flex flex-wrap items-center space-y-1 sm:space-y-0">
          <div class="w-full sm:pr-2 sm:w-1/2">
            <label for="first-name" class="hidden">Fist Name</label>
            <input
              id="first-name"
              v-model="formData.firstName"
              type="text"
              placeholder="First Name"
              class="w-full px-4 py-2 border rounded-lg appearance-none border-dark-black border-opacity-40 focus:outline-none focus:border-blue-300"
            />
          </div>
          <div class="w-full sm:pl-2 sm:w-1/2">
            <label for="last-name" class="hidden">Last Name</label>
            <input
              id="last-name"
              v-model="formData.lastName"
              type="text"
              placeholder="Last Name"
              class="w-full px-4 py-2 border rounded-lg appearance-none border-dark-black border-opacity-40 focus:outline-none focus:border-blue-300"
            />
          </div>
        </div>
        <div>
          <label for="country" class="font-sans uppercase">
            Country/Region
          </label>
          <select
            id="country"
            v-model="formData.country"
            name="country"
            class="w-full px-4 py-2 border rounded-lg border-dark-black border-opacity-40 focus:outline-none focus:border-blue-300"
          >
            <option
              v-for="(country, index) in getCountryList"
              :key="index"
              :value="country"
              class="w-full"
            >
              {{ country }}
            </option>
          </select>
        </div>
        <div>
          <label for="date-of-birth" class="hidden">Date of Birth</label>
          <input
            id="date-of-birth"
            v-model="formData.dateOfBirth"
            type="text"
            onfocus="(this.type='date')"
            onblur="(this.type='text')"
            placeholder="Date of Birth"
            class="w-full px-4 py-2 border rounded-lg appearance-none border-dark-black border-opacity-40 focus:outline-none focus:border-blue-300"
          />
        </div>
        <div>
          <label for="email" class="hidden">Email</label>
          <input
            id="email"
            v-model="formData.email"
            type="password"
            placeholder="Email"
            class="w-full px-4 py-2 border rounded-lg appearance-none border-dark-black border-opacity-40 focus:outline-none focus:border-blue-300"
          />
        </div>
        <div
          class="flex flex-wrap items-center space-y-1 text-base sm:space-y-0"
        >
          <div class="w-full sm:pr-2 sm:w-1/2">
            <label for="password" class="hidden">Password</label>
            <input
              id="password"
              v-model="formData.password"
              type="password"
              placeholder="Password"
              class="w-full px-4 py-2 border rounded-lg appearance-none border-dark-black border-opacity-40 focus:outline-none focus:border-blue-300"
            />
          </div>
          <div class="w-full sm:pl-2 sm:w-1/2">
            <label for="password2" class="hidden">Confirm password</label>
            <input
              id="password2"
              v-model="formData.password2"
              type="text"
              placeholder="Confirm password"
              class="w-full px-4 py-2 border rounded-lg appearance-none border-dark-black border-opacity-40 focus:outline-none focus:border-blue-300"
            />
          </div>
        </div>
        <button
          type="submit"
          class="flex justify-center py-2 space-x-3 transition duration-300 transform rounded-lg hover:scale-105 sm:py-3 focus:outline-none bg-primary ring-black ring-1"
        >
          <div class="text-white">Register</div>
        </button>
      </form>
    </div>
  </div>
</template>

<script>
import { countries } from 'countries-list'

export default {
  data() {
    return {
      formData: {
        username: '',
        firstName: '',
        lastName: '',
        email: '',
        dateOfBirth: '',
        country: '',
        password: '',
        password2: '',
      },
    }
  },
  head: () => ({ title: 'Register' }),
  computed: {
    getCountryList() {
      const countryList = []
      for (const country in countries) {
        countryList.push(countries[country].name)
      }
      return countryList
    },
  },
  methods: {
    submitForm() {
      const formData = this.formData
      if (formData.password !== formData.password2) {
        alert('The two passwords do not match')
      }
      this.$axios
        .$post('/accounts/register', null, { params: formData })
        .then((_result) => {
          this.$addAlert({
            severity: 'info',
            messageHeading: 'Registration successful',
            messageBody: 'Please login with your new account now',
            active: true,
          })
          this.$router.push({ name: 'accounts-login' })
        })
        .catch((err) => {
          this.$addAlert({
            severity: 'warning',
            messageHeading: 'Invalid input',
            messageBody: err.response.data.message,
            active: true,
          })
        })
    },
  },
}
</script>
