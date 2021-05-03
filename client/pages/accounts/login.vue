<template>
  <div class="bg-cream-white">
    <div
      class="container flex flex-wrap-reverse items-center h-full mx-auto place-content-center sm:place-content-evenly"
    >
      <div class="w-full px-6 sm:w-1/2 sm:px-0">
        <form
          class="flex flex-col font-poppins max-w-sm sm:max-w-[25rem] md:max-w-md mx-auto md:text-xl sm:text-lg space-y-7 sm:space-y-16"
          @submit.prevent="submitForm"
        >
          <div class="flex flex-col w-full space-y-3 sm:space-y-5">
            <div
              class="mt-10 mb-5 text-3xl font-bold sm:text-4xl sm:mb-10 font-poppins text-dark-black"
            >
              Please Sign In.
            </div>
            <div class="flex flex-col space-y-5 sm:space-y-7">
              <div>
                <input
                  id="username"
                  v-model="formData.username"
                  type="text"
                  placeholder="Resourceium username"
                  class="w-full px-4 py-2 bg-transparent border appearance-none rounded-15px hover:shadow-lg border-dark-black border-opacity-40 focus:outline-none focus:border-blue-300"
                />
                <label for="username" class="hidden">Username</label>
              </div>
              <div>
                <input
                  id="password"
                  v-model="formData.password"
                  type="password"
                  placeholder="Password"
                  class="w-full px-4 py-2 bg-transparent border appearance-none rounded-15px hover:shadow-lg border-dark-black border-opacity-40 focus:outline-none focus:border-blue-300"
                />
                <label for="password" class="hidden">Password</label>
                <div class="mt-3 text-sm">
                  Your Resourceium ID is the username you use to sign in.
                </div>
              </div>
            </div>
          </div>
          <div class="flex flex-col space-y-5">
            <button
              type="submit"
              class="flex justify-center py-2 space-x-3 transition duration-300 transform rounded-15px hover:scale-105 sm:py-3 focus:outline-none bg-primary ring-black ring-1"
            >
              <div class="text-white">Login</div>
            </button>
            <div class="text-sm sm:text-base text-primary">
              <div>
                <NuxtLink :to="{ name: 'index' }" class="hover:underline">
                  Forgot your Resourceium username or password?
                </NuxtLink>
              </div>
              <div>
                <NuxtLink
                  :to="{ name: 'accounts-register' }"
                  class="hover:underline"
                >
                  Don't have a Resourceium account? Create one here.
                </NuxtLink>
              </div>
            </div>
          </div>
        </form>
      </div>
      <div class="w-full sm:w-1/2">
        <img
          :src="require('~/assets/images/logo.png')"
          alt="Resourceium logo"
          class="h-48 mx-auto sm:h-80 md:h-96 xl:h-[28rem]"
          rel="preload"
        />
      </div>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    const mode = process.env.NODE_ENV
    if (mode === 'development') {
      return {
        formData: {
          username: '',
          password: '1passWord$',
        },
      }
    } else {
      return {
        formData: {
          username: '',
          password: '',
        },
      }
    }
  },
  head: () => ({ title: 'Login' }),
  mounted() {
    if (
      window.localStorage.getItem('username') &&
      window.localStorage.getItem('userId')
    ) {
      this.$addAlert({
        message: 'You are already logged in',
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
            message: 'Login successful',
            type: 'success',
          })
          window.localStorage.setItem('username', result.name)
          window.localStorage.setItem('userId', result.id)
          this.redirectHome()
        })
        .catch((err) => {
          if (err.response.status === 404) {
            this.$addAlert({
              message: 'No such user found',
              type: 'danger',
            })
          }
          if (err.response.status === 400) {
            this.$addAlert({
              message: 'Incorrect credentials',
              type: 'warning',
            })
          }
        })
    },
  },
}
</script>
