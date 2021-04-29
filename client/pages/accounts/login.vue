<template>
  <div
    class="container flex flex-wrap-reverse items-center h-screen mx-auto bg-cream-white place-content-center sm:place-content-evenly"
  >
    <div class="w-full px-6 sm:w-1/2 sm:px-0">
      <form
        class="flex flex-col font-poppins max-w-sm sm:max-w-[25rem] md:max-w-md mx-auto space-y-5 md:text-xl sm:text-lg"
        @submit.prevent="submitForm"
      >
        <div
          class="mt-10 mb-5 text-3xl font-bold sm:text-4xl sm:mb-10 font-poppins text-dark-black"
        >
          Please Sign In.
        </div>
        <input
          id="username"
          v-model="formData.name"
          type="text"
          placeholder="Resourceium username"
          class="px-4 py-2 border rounded-lg appearance-none border-dark-black border-opacity-40 focus:outline-none focus:border-blue-300"
        />
        <label for="username" class="hidden">Username</label>
        <input
          id="password"
          v-model="formData.password"
          type="text"
          placeholder="Password"
          class="px-4 py-2 border rounded-lg appearance-none border-dark-black border-opacity-40 focus:outline-none focus:border-blue-300"
        />
        <label for="password" class="hidden">Password</label>
        <button
          type="submit"
          class="flex justify-center py-2 space-x-3 transition duration-300 transform rounded-lg hover:scale-105 sm:py-3 focus:outline-none bg-primary ring-black ring-1"
        >
          <div class="text-white">Login</div>
        </button>
        <div class="text-sm sm:text-base font-styled text-primary">
          <div>
            <NuxtLink :to="{ name: 'index' }" class="hover:underline">
              Don't have a Resourceium account? Create one here.
            </NuxtLink>
          </div>
          <div>
            <NuxtLink :to="{ name: 'index' }" class="hover:underline">
              Forgot your Resourceium username or password?
            </NuxtLink>
          </div>
        </div>
      </form>
    </div>
    <div class="w-full sm:w-1/2">
      <img
        :src="require('~/assets/images/logo.png')"
        alt="Resourceium logo"
        class="h-48 mx-auto sm:h-80 md:h-96 xl:h-[30rem]"
      />
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      formData: {
        name: '',
        password: '',
      },
    }
  },
  head: () => ({ title: 'Login' }),
  mounted() {
    if (
      window.localStorage.getItem('username') &&
      window.localStorage.getItem('userId')
    ) {
      this.$addAlert({
        severity: 'warning',
        messageHeading: 'Logged In',
        messageBody: 'You are already logged in',
        active: true,
      })
      this.redirectHome()
    }
  },
  methods: {
    redirectHome() {
      this.$router.push({ name: 'index' })
    },
    submitForm() {
      const formData = this.formData
      this.$axios
        .$post('/accounts/login', null, { params: formData })
        .then((result) => {
          this.$addAlert({
            severity: 'info',
            messageHeading: 'Login successful',
            active: true,
          })
          window.localStorage.setItem('username', result.name)
          window.localStorage.setItem('userId', result.id)
          this.redirectHome()
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
