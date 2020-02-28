/* file: pi.c        George Moody (george@mit.edu)	8 January 2000

Compute pi to arbitrary precision using Machin's formula:
   pi/4 = 4 arccot(5) - arccot(239)
The computations are done in base 10000, so that 4 decimal digits can
be kept in one 16-bit short integer.

This is based on a program by Roy Williams, but requires less memory
(only 2 bytes per additional digit computed).  It's designed to be portable,
self-contained and relatively easy to read even for those who have little
experience with C.  Further improvements are left as an exercise for the
reader :-)

On a 400 MHz PII with plenty of memory, this computes 100,000 digits in about
11 minutes of CPU time.  Running time is proportional to the square of the
number of digits (until you run out of memory, that is).  Be sure you're Y10K
compliant before trying much more than a billion digits.
*/

#include <stdio.h>

typedef short bigdigit;	/* ANSI C short integers are at least 16 bits */
typedef short *bignum;	/* a bignum is a string of bigdigits */
long len;		/* number of bigdigits per bignum (set by user) */
#define DPB   4		/* 4 decimal digits per bigdigit; note that print()
			   (below) assumes that DPB is 4 */
#define BASE  10000	/* base for computation (10^DPB) */

bignum makebignum()	/* allocate memory for a bignum */
{
  if (len <= 0) return NULL;
  return ((bignum)malloc(len*sizeof(bigdigit)));
}

bignum s, t;	/* needed by arccot, allocated in main */

arccot(bignum n, int i)
{
  int ii = i*i, j = 1;

  set(n, 1);		/* n = 1 */
  div(n, i);		/* n = 1/i */
  copy(s, n);		/* s = 1/i */
  do {
    div(s, ii);		/* s = 1/i^3, 1/i^5, 1/i^7, ... */
    copy(t, s);
    div(t, 2*j+1);	/* t = 1/3i^3, 1/5i^5, 1/7i^7, ... */
    if (j & 1)		/* if j is odd ... */
      sub(n, t);	/* ... decrease n by t */
    else		/* if j is even ... */
      add(n, t);	/* ... increase n by t */
    j++;		/* increase j by 1 */
  } while (nonzero(t));  /* repeat (from the "do") if t has not vanished yet */
}

set(bignum to, int from)
{
  int i;

  to[0] = from;
  for (i = 1; i < len; i++)
    to[i] = 0;
}

copy(bignum to, bignum from)
{
  memcpy(to, from, len*sizeof(short));
}

nonzero(bignum n)
{
  long i = len;

  while (--i >= 0)
    if (n[i]) return (1);
  return (0);
}

add(bignum n, bignum delta)
{
  long i = len;

  while (--i >= 0) {
    n[i] += delta[i];
    if (n[i] >= BASE) {		/* carry */
      n[i] -= BASE;
      n[i-1]++;
    }
  }
}

sub(bignum n, bignum delta)
{
  long i = len;

  while (--i >= 0) {
    n[i] -= delta[i];
    if (n[i] <= 0) {		/* borrow */
      n[i] += BASE;
      n[i-1]--;
    }
  }
}

mult(bignum n, long factor)
{
  long carry = 0, i = len, p;

  while (--i >= 0) {
    p = n[i] * factor + carry;
    n[i] = p % BASE;	/* "p % BASE" means "p modulo BASE" */
    carry = p / BASE;
  }
}

div(bignum n, long denom)
{
  long carry = 0, i = 0, num;

  while (i < len) {
    num = n[i] + carry;
    n[i++] = num / denom;
    carry = (num % denom) * BASE;
  }
}

print(bignum n)
{
  long i = 1;

  printf("%3d.", n[0]);
  while (i < len-1) {
    printf("%04d", n[i]);
    if ((++i)%16 == 0) printf("\n");
  }
  printf("\n");
}

main(int argc, char **argv)
{
  bignum a, b;
  int digits = 20;

  /* Set up */
  if (argc > 1 && (digits = atoi(argv[1])) <= 20) {
    fprintf(stderr, "usage: %s [N]\n", argv[0]);
    exit(1);
  }
  len = digits/DPB + 1;   /* set the precision; the extra unprinted bigdigit
			     ensures accuracy for the last printed decimal
			     digits. */
  if (((a = makebignum()) == NULL) || ((b = makebignum()) == NULL) ||
      ((s = makebignum()) == NULL) || ((t = makebignum()) == NULL)) {
    fprintf(stderr, "%s: not enough memory\n", argv[0]);
    exit(2);
  }

  /* Compute */
  arccot(a, 5);		/* a = arctan(1/5) */
  mult(a, 4);		/* a = 4 arctan(1/5) */
  arccot(b, 239);	/* b = arctan(1/239) */
  sub(a, b);		/* a = 4 arctan(1/5) - arctan(1/239) ~= pi/4 */
  mult(a, 4);           /* a = 16 arctan(1/5) - 4 arctan(1/239) ~= pi */ 
  print(a);		/* 3.1415926535897932384626433832... */
  exit(0);
}
