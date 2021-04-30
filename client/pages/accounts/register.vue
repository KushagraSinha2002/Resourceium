<template>
  <div class="h-screen mx-auto bg-cream-white">
    <div
      class="container flex items-center h-full px-6 mx-auto sm:px-0 place-content-center sm:place-content-evenly"
    >
      <form
        class="flex flex-col font-poppins font-light max-w-sm sm:max-w-[25rem] md:max-w-md mx-auto space-y-5 md:text-xl sm:text-lg"
        @submit.prevent="submitForm"
      >
        <div
          class="flex items-center justify-center mt-10 mb-5 space-x-4 text-3xl font-bold text-center sm:text-4xl sm:mb-10 font-poppins text-primary"
        >
          <div>Create your</div>
          <img
            :src="require('~/assets/images/logo.png')"
            alt="Resourceium logo"
            class="h-10 sm:h-14"
            rel="preload"
            align="middle"
          />
          <div>ID.</div>
        </div>
        <NuxtLink
          :to="{ name: 'index' }"
          class="text-xs text-center hover:underline"
        >
          <div>Already have a Resourceium account?</div>
          <div>Find it here.</div>
        </NuxtLink>
        <div>
          <label for="username" class="hidden">Username</label>
          <input
            id="username"
            v-model="formData.username"
            type="text"
            placeholder="Username"
            class="w-full px-4 py-2 bg-transparent border appearance-none hover:shadow-lg rounded-15px border-dark-black border-opacity-40 focus:outline-none focus:border-blue-300"
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
              class="w-full px-4 py-2 bg-transparent border appearance-none hover:shadow-lg rounded-15px border-dark-black border-opacity-40 focus:outline-none focus:border-blue-300"
            />
          </div>
          <div class="w-full sm:pl-2 sm:w-1/2">
            <label for="last-name" class="hidden">Last Name</label>
            <input
              id="last-name"
              v-model="formData.lastName"
              type="text"
              placeholder="Last Name"
              class="w-full px-4 py-2 bg-transparent border appearance-none hover:shadow-lg rounded-15px border-dark-black border-opacity-40 focus:outline-none focus:border-blue-300"
            />
          </div>
        </div>
        <div>
          <label for="country" class="text-sm uppercase font-poppins">
            Country/Region
          </label>
          <select
            id="country"
            v-model="formData.country"
            name="country"
            class="w-full px-4 py-2 bg-transparent border rounded-15px border-dark-black border-opacity-40 focus:outline-none focus:border-blue-300"
          >
            <option value="" selected disabled hidden>Select an Option</option>
            <option
              v-for="(country, index) in getCountryList"
              :key="index"
              :value="country"
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
            class="w-full px-4 py-2 bg-transparent border appearance-none hover:shadow-lg rounded-15px border-dark-black border-opacity-40 focus:outline-none focus:border-blue-300"
          />
        </div>
        <div>
          <label for="email" class="hidden">Email</label>
          <input
            id="email"
            v-model="formData.email"
            type="email"
            placeholder="Email"
            class="w-full px-4 py-2 bg-transparent border appearance-none hover:shadow-lg rounded-15px border-dark-black border-opacity-40 focus:outline-none focus:border-blue-300"
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
              class="w-full px-4 py-2 bg-transparent border appearance-none hover:shadow-lg rounded-15px border-dark-black border-opacity-40 focus:outline-none focus:border-blue-300"
            />
          </div>
          <div class="w-full sm:pl-2 sm:w-1/2">
            <label for="password2" class="hidden">Confirm password</label>
            <input
              id="password2"
              v-model="formData.password2"
              type="password"
              placeholder="Confirm password"
              class="w-full px-4 py-2 bg-transparent border appearance-none hover:shadow-lg rounded-15px border-dark-black border-opacity-40 focus:outline-none focus:border-blue-300"
            />
          </div>
        </div>
        <button
          type="submit"
          class="flex justify-center py-2 space-x-3 transition duration-300 transform rounded-15px hover:scale-105 sm:py-3 focus:outline-none bg-primary ring-black ring-1"
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
      countryList.sort((val1, val2) => val1.localeCompare(val2))
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
